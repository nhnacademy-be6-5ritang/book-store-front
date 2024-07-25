package com.nhnacademy.bookstorefront.couponpolicy.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyCreateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyUpdateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.response.CouponPolicyResponseDTO;
import com.nhnacademy.bookstorefront.couponpolicy.service.CouponPolicyService;
import com.nhnacademy.bookstorefront.global.config.CacheConfig;
import com.nhnacademy.bookstorefront.global.controller.GlobalExceptionHandler;

@WebMvcTest(CouponPolicyController.class)
@AutoConfigureMockMvc(addFilters = false)
class CouponPolicyControllerTest {

	@MockBean
	private MockMvc mockMvc;



	@MockBean
	private CouponPolicyService couponPolicyService;

	@MockBean
	private CacheConfig cacheConfig;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new CouponPolicyController(couponPolicyService))
			.setControllerAdvice(GlobalExceptionHandler.class)
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
	}

	@Test
	void testCreateCouponPolicy_Success() throws Exception {
		// given
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "welcome", null, null, null, null
		);

		doNothing().when(couponPolicyService).issueWelcomeCoupon(requestDTO);

		// when
		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
				.param("type", requestDTO.type())
				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/coupons/policies"))
			.andDo(print());

		// then
		verify(couponPolicyService, times(1)).issueWelcomeCoupon(any(CouponPolicyCreateRequestDTO.class));
	}

	@Test
	void testCreateCouponPolicy_Success_Birthday() throws Exception {
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "birthday", null, null, null, null
		);

		doNothing().when(couponPolicyService).issueBirthdayCoupon(requestDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
				.param("type", requestDTO.type())
				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/coupons/policies"))
			.andDo(MockMvcResultHandlers.print());

		verify(couponPolicyService, times(1)).issueBirthdayCoupon(any(CouponPolicyCreateRequestDTO.class));
	}

	@Test
	void testCreateCouponPolicy_Success_Book() throws Exception {
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "book", 1L, "Some Book", null, null
		);

		doNothing().when(couponPolicyService).issueBookCoupon(requestDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
				.param("type", requestDTO.type())
				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/coupons/policies"))
			.andDo(MockMvcResultHandlers.print());

		verify(couponPolicyService, times(1)).issueBookCoupon(any(CouponPolicyCreateRequestDTO.class));
	}

	@Test
	void testCreateCouponPolicy_Success_Category() throws Exception {
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "category", null, null, 1L,
			"Some Category"
		);

		doNothing().when(couponPolicyService).issueCategoryCoupon(requestDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
				.param("type", requestDTO.type())
				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/coupons/policies"))
			.andDo(MockMvcResultHandlers.print());

		verify(couponPolicyService, times(1)).issueCategoryCoupon(any(CouponPolicyCreateRequestDTO.class));
	}

	@Test
	void testCreateCouponPolicy_Success_Sale() throws Exception {
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "sale", null, null, null, null
		);

		doNothing().when(couponPolicyService).issueSaleCoupon(requestDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
				.param("type", requestDTO.type())
				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/coupons/policies"))
			.andDo(MockMvcResultHandlers.print());

		verify(couponPolicyService, times(1)).issueSaleCoupon(any(CouponPolicyCreateRequestDTO.class));
	}

	@Test
	void testCreateCouponPolicy_ValidationException() throws Exception {
		// Given
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), BigDecimal.valueOf(0.2), BigDecimal.valueOf(1000),
			"welcome", null, null, null, null
		);

		// When
		ResultActions resultActions = mockMvc.perform(post("/coupons/policies")
			.param("minOrderPrice", requestDTO.minOrderPrice().toString())
			.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
			.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
			.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
			.param("type", requestDTO.type())
			.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
			.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
			.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
			.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : "")
		);

		// Then
		resultActions.andExpect(status().isBadRequest())
			.andExpect(view().name("global/error"))
			.andExpect(model().attributeExists("message"))
			.andExpect(model().attributeExists("status"))
			.andExpect(model().attributeExists("timestamp"))
			.andDo(print());
	}

	@Test
	void testCreateCouponPolicy_BookIdNullException() throws Exception {
		// Given
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "book", null, "Some Book", null, null
		);

		ResultActions resultActions = mockMvc.perform(post("/coupons/policies")
			.param("minOrderPrice", requestDTO.minOrderPrice().toString())
			.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
			.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
			.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
			.param("type", requestDTO.type())
			.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
			.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
			.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
			.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : "")
		);

		// Then
		resultActions.andExpect(status().isBadRequest())
			.andExpect(view().name("global/error"))
			.andExpect(model().attributeExists("message"))
			.andExpect(model().attributeExists("status"))
			.andExpect(model().attributeExists("timestamp"))
			.andDo(print());
	}

	@Test
	void testCreateCouponPolicy_CategoryIdNullException() throws Exception {
		// Given
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "category", null, null, null, null
		);

		// When
		ResultActions resultActions = mockMvc.perform(post("/coupons/policies")
			.param("minOrderPrice", requestDTO.minOrderPrice().toString())
			.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
			.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
			.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
			.param("type", requestDTO.type())
			.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
			.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
			.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
			.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : "")
		);

		// Then
		resultActions.andExpect(status().isBadRequest())
			.andExpect(view().name("global/error"))
			.andExpect(model().attributeExists("message"))
			.andExpect(model().attributeExists("status"))
			.andExpect(model().attributeExists("timestamp"))
			.andDo(print());
	}

	@Test
	void testCreateCouponPolicy_TypeNotExistException() throws Exception {
		// Given
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "unknown", null, null, null, null
		);

		// When
		ResultActions resultActions = mockMvc.perform(post("/coupons/policies")
			.param("minOrderPrice", requestDTO.minOrderPrice().toString())
			.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
			.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
			.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
			.param("type", requestDTO.type())
			.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
			.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
			.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
			.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : "")
		);

		// Then
		resultActions.andExpect(status().isBadRequest())
			.andExpect(view().name("global/error"))
			.andExpect(model().attributeExists("message"))
			.andExpect(model().attributeExists("status"))
			.andExpect(model().attributeExists("timestamp"))
			.andDo(print());
	}

	@Test
	void testUpdateCouponPolicy_Success() throws Exception {
		// given
		CouponPolicyUpdateRequestDTO requestDTO = new CouponPolicyUpdateRequestDTO(
			BigDecimal.valueOf(10000), BigDecimal.valueOf(5000), null, null, true
		);

		doNothing().when(couponPolicyService).updateCouponPolicy(1L, requestDTO);
		// when
		mockMvc.perform(patch("/coupons/policies/1")
				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
				.param("isUsed", requestDTO.isUsed().toString()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/coupons/policies"))
			.andDo(MockMvcResultHandlers.print());

		// then
		verify(couponPolicyService, times(1)).updateCouponPolicy(eq(1L), any(CouponPolicyUpdateRequestDTO.class));
	}

	@Test
	void testUpdateCouponPolicy_ValidationException() throws Exception {
		// Given: 유효하지 않은 requestDTO 데이터 (예: salePrice와 saleRate가 동시에 설정됨)
		CouponPolicyUpdateRequestDTO requestDTO = new CouponPolicyUpdateRequestDTO(
			BigDecimal.valueOf(10000), BigDecimal.valueOf(1000), BigDecimal.valueOf(0.2), BigDecimal.valueOf(500), true
		);

		// When & Then: Patch 요청을 보내고 ValidationException 발생을 검증
		mockMvc.perform(patch("/coupons/policies/1")  // couponPolicyId가 1로 설정됨
				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
				.param("isUsed", requestDTO.isUsed().toString())
			)
			.andExpect(status().isBadRequest())  // 예상되는 상태 코드
			.andExpect(view().name("global/error"))  // 예외 발생 시 뷰 이름
			.andExpect(model().attributeExists("message"))
			.andExpect(model().attributeExists("status"))
			.andExpect(model().attributeExists("timestamp"))
			.andDo(print());
	}






	@Test
	void testGetCouponPolicies() throws Exception {
		// Given: Mock 데이터를 생성
		CouponPolicyResponseDTO page1 = new CouponPolicyResponseDTO(
			1L, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
			null, null, "welcome", true, null, null, null, null
		);

		Page<CouponPolicyResponseDTO> mockPage = new PageImpl<>(
			List.of(page1), PageRequest.of(0, 3), 1
		);

		// Mock 서비스 호출
		when(couponPolicyService.getAllCouponPolicies(PageRequest.of(0, 3)))
			.thenReturn(mockPage);

		// Act & Assert
		mockMvc.perform(get("/coupons/policies")
				.param("page", "0")
				.param("size", "3"))
			.andExpect(status().isOk())
			.andExpect(view().name("coupon-manager/coupon-policy"))
			.andExpect(model().attributeExists("policies"))
			.andExpect(model().attribute("policies", mockPage));
	}
}

