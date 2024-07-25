package com.nhnacademy.bookstorefront.coupontemplate.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.request.CouponTemplateCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.response.CouponTemplateResponseDTO;
import com.nhnacademy.bookstorefront.coupontemplate.feignclient.CouponTemplateFeignClient;

@ExtendWith(MockitoExtension.class)
class CouponTemplateServiceImplTest {

	@Mock
	private CouponTemplateFeignClient couponTemplateFeignClient;

	@InjectMocks
	private CouponTemplateServiceImpl couponTemplateServiceImpl;

	@BeforeEach
	void setUp() {
		// MockitoExtension handles initialization
	}

	@Test
	void testCreateCouponTemplate() {
		// Given
		CouponTemplateCreateRequestDTO requestDTO = new CouponTemplateCreateRequestDTO(
			1L,
			LocalDateTime.now().plusDays(30),
			LocalDateTime.now().minusDays(30),
		3L
		);

		// When
		couponTemplateServiceImpl.createCouponTemplate(requestDTO);

		// Then
		verify(couponTemplateFeignClient, times(1)).createCouponTemplate(requestDTO);
	}

	@Test
	void testGetAllCouponTemplatesByManagerPaging() {
		// Given
		Pageable pageable = Pageable.unpaged();
		CouponTemplateResponseDTO dto1 = new CouponTemplateResponseDTO(
			1L,
			1L,
			BigDecimal.valueOf(100),
			BigDecimal.valueOf(20),
			null,
			null,
			"welcome",
			true,
			null,
			null,
			null,
			null,
			LocalDateTime.now().plusDays(30),
			LocalDateTime.now().minusDays(30),
			10L
		);
		Page<CouponTemplateResponseDTO> page = new PageImpl<>(List.of(dto1));

		when(couponTemplateFeignClient.getAllCouponTemplatesByManagerPaging(pageable)).thenReturn(ResponseEntity.ok(page));

		// When
		Page<CouponTemplateResponseDTO> result = couponTemplateServiceImpl.getAllCouponTemplatesByManagerPaging(pageable);

		// Then
		assertEquals(page, result);
	}

	@Test
	void testGetAllCouponTemplatesByUserPaging() {
		// Given
		Pageable pageable = Pageable.unpaged();
		CouponTemplateResponseDTO dto1 = new CouponTemplateResponseDTO(
			1L,
			1L,
			BigDecimal.valueOf(100),
			BigDecimal.valueOf(20),
			null,
			null,
			"welcome",
			true,
			null,
			null,
			null,
			null,
			LocalDateTime.now().plusDays(30),
			LocalDateTime.now().minusDays(30),
			10L
		);
		Page<CouponTemplateResponseDTO> page = new PageImpl<>(List.of(dto1));

		when(couponTemplateFeignClient.getAllCouponTemplatesByUserPaging(pageable)).thenReturn(ResponseEntity.ok(page));

		// When
		Page<CouponTemplateResponseDTO> result = couponTemplateServiceImpl.getAllCouponTemplatesByUserPaging(pageable);

		// Then
		assertEquals(page, result);
	}
}