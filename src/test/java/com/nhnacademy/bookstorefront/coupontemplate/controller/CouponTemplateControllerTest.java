package com.nhnacademy.bookstorefront.coupontemplate.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nhnacademy.bookstorefront.cache.service.CacheService;
import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.request.CouponTemplateCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.response.CouponTemplateResponseDTO;
import com.nhnacademy.bookstorefront.coupontemplate.service.CouponTemplateService;
import com.nhnacademy.bookstorefront.global.controller.GlobalDataControllerAdvice;

@WebMvcTest(CouponTemplateController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CouponTemplateControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CouponTemplateService couponTemplateService;

	@MockBean
	private CacheService cacheService;

	@MockBean
	private GlobalDataControllerAdvice globalDataControllerAdvice;

	@Test
	void testCreateCoupon() throws Exception {
		CouponTemplateCreateRequestDTO requestDTO = new CouponTemplateCreateRequestDTO(
			1L, LocalDateTime.now().plusDays(1), LocalDateTime.now(), 50L
		);

		doNothing().when(couponTemplateService).createCouponTemplate(requestDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/coupons")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("expiredDate", String.valueOf(requestDTO.expiredDate()))
				.param("issueDate", String.valueOf(requestDTO.issueDate()))
				.param("couponPolicyId", String.valueOf(requestDTO.couponPolicyId()))
				.param("quantity", String.valueOf(requestDTO.quantity())))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/coupons/policies"));

		verify(couponTemplateService, times(1)).createCouponTemplate(any(CouponTemplateCreateRequestDTO.class));
	}

	@Test
	void testGetAllCouponTemplates() throws Exception {
		Page<CouponTemplateResponseDTO> coupons = new PageImpl<>(List.of(new CouponTemplateResponseDTO(
			1L, 1L, new BigDecimal("100.00"), new BigDecimal("10.00"),
			null, null, "sale",
			true, null, null, null, null,
			LocalDateTime.now().plusDays(30), LocalDateTime.now(), 500L
		)));


		when(couponTemplateService.getAllCouponTemplatesByManagerPaging(any(PageRequest.class))).thenReturn(coupons);

		mockMvc.perform(MockMvcRequestBuilders.get("/coupons")
				.param("page", "0")
				.param("size", "10"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("coupon-manager/coupon-template"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("coupons"));

		verify(couponTemplateService, times(1)).getAllCouponTemplatesByManagerPaging(any(PageRequest.class));
	}

	@Test
	void testGetAllCouponTemplatesIssuePaging() throws Exception {
		Page<CouponTemplateResponseDTO> couponTemplates = new PageImpl<>(List.of(new CouponTemplateResponseDTO(
			1L, 1L, new BigDecimal("100.00"), new BigDecimal("10.00"),
			null, null, "sale",
			true, null, null, null, null,
			LocalDateTime.now().plusDays(30), LocalDateTime.now(), 500L
		)));


		when(couponTemplateService.getAllCouponTemplatesByUserPaging(any(PageRequest.class))).thenReturn(couponTemplates);

		mockMvc.perform(MockMvcRequestBuilders.get("/coupons/issue")
				.param("page", "1")
				.param("size", "3"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("coupon-user/user-coupon-issue"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("couponTemplates"));

		verify(couponTemplateService, times(1)).getAllCouponTemplatesByUserPaging(any(PageRequest.class));
	}
}