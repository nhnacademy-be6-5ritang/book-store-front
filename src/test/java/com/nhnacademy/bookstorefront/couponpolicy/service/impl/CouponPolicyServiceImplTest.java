package com.nhnacademy.bookstorefront.couponpolicy.service.impl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyCreateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyUpdateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.response.CouponPolicyResponseDTO;
import com.nhnacademy.bookstorefront.couponpolicy.feignclient.CouponPolicyServiceFeignClient;

 class CouponPolicyServiceImplTest {

	@Mock
	private CouponPolicyServiceFeignClient couponPolicyServiceFeignClient;

	@InjectMocks
	private CouponPolicyServiceImpl couponPolicyServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testIssueWelcomeCoupon() {
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(100), BigDecimal.valueOf(10), null,
			null, "welcome", null, null, null, null
		);
		CouponPolicyResponseDTO responseDTO = new CouponPolicyResponseDTO(
			1L, BigDecimal.valueOf(100), BigDecimal.valueOf(10), null,
			null, "welcome", true, null, null, null, null
		);
		when(couponPolicyServiceFeignClient.issueWelcomeCoupon(any(CouponPolicyCreateRequestDTO.class)))
			.thenReturn(ResponseEntity.ok(responseDTO));

		// When
		couponPolicyServiceImpl.issueWelcomeCoupon(requestDTO);

		// Then
		verify(couponPolicyServiceFeignClient, times(1)).issueWelcomeCoupon(requestDTO);
	}

	@Test
	void testIssueBirthdayCoupon() {
		// Given
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(100), BigDecimal.valueOf(10), null,
			null, "birthday", null, null, null, null
		);
		CouponPolicyResponseDTO responseDTO = new CouponPolicyResponseDTO(
			1L, BigDecimal.valueOf(100), BigDecimal.valueOf(10), null,
			null, "birthday", true, null, null, null, null
		);
		when(couponPolicyServiceFeignClient.issueBirthdayCoupon(any(CouponPolicyCreateRequestDTO.class)))
			.thenReturn(ResponseEntity.ok(responseDTO));

		// When
		couponPolicyServiceImpl.issueBirthdayCoupon(requestDTO);

		// Then
		verify(couponPolicyServiceFeignClient, times(1)).issueBirthdayCoupon(requestDTO);
	}

	@Test
	void testIssueBookCoupon() {
		// Given
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(100), BigDecimal.valueOf(10), null,
			null, "book", 1L, "title", null, null
		);
		CouponPolicyResponseDTO responseDTO = new CouponPolicyResponseDTO(
			1L, BigDecimal.valueOf(100), BigDecimal.valueOf(10), null,
			null, "book", true, 1L, "title", null, null
		);
		when(couponPolicyServiceFeignClient.issueSpecificBookCoupon(any(CouponPolicyCreateRequestDTO.class)))
			.thenReturn(ResponseEntity.ok(responseDTO));

		// When
		couponPolicyServiceImpl.issueBookCoupon(requestDTO);

		// Then
		verify(couponPolicyServiceFeignClient, times(1)).issueSpecificBookCoupon(requestDTO);
	}

	@Test
	void testIssueCategoryCoupon() {
		// Given
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(100), BigDecimal.valueOf(10), null,
			null, "category", null, null, 1L, "name"
		);
		CouponPolicyResponseDTO responseDTO = new CouponPolicyResponseDTO(
			1L, BigDecimal.valueOf(100), BigDecimal.valueOf(10), null,
			null, "category", true, null, null, 1L, "name"
		);
		when(couponPolicyServiceFeignClient.issueSpecificCategoryCoupon(any(CouponPolicyCreateRequestDTO.class)))
			.thenReturn(ResponseEntity.ok(responseDTO));

		// When
		couponPolicyServiceImpl.issueCategoryCoupon(requestDTO);

		// Then
		verify(couponPolicyServiceFeignClient, times(1)).issueSpecificCategoryCoupon(requestDTO);
	}

	@Test
	void testIssueSaleCoupon() {
		// Given
		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
			BigDecimal.valueOf(100), BigDecimal.valueOf(10), null,
			null, "sale", null, null, null, null
		);
		CouponPolicyResponseDTO responseDTO = new CouponPolicyResponseDTO(
			1L, BigDecimal.valueOf(100), BigDecimal.valueOf(10), null,
			null, "sale", true, null, null, null, null
		);
		when(couponPolicyServiceFeignClient.issueDiscountCoupon(any(CouponPolicyCreateRequestDTO.class)))
			.thenReturn(ResponseEntity.ok(responseDTO));

		// When
		couponPolicyServiceImpl.issueSaleCoupon(requestDTO);

		// Then
		verify(couponPolicyServiceFeignClient, times(1)).issueDiscountCoupon(requestDTO);
	}

	@Test
	void testUpdateCouponPolicy() {
		// Given
		Long couponPolicyId = 1L;
		CouponPolicyUpdateRequestDTO requestDTO = new CouponPolicyUpdateRequestDTO(
			BigDecimal.valueOf(100), BigDecimal.valueOf(10), null,
			null, true
		);
		when(couponPolicyServiceFeignClient.updateCouponPolicy(anyLong(), any(CouponPolicyUpdateRequestDTO.class)))
			.thenReturn(ResponseEntity.ok().build());

		// When
		couponPolicyServiceImpl.updateCouponPolicy(couponPolicyId, requestDTO);

		// Then
		verify(couponPolicyServiceFeignClient, times(1)).updateCouponPolicy(couponPolicyId, requestDTO);
	}

	@Test
	void testGetAllCouponPolicies() {
		// Given
		Pageable pageable = Pageable.unpaged();
		CouponPolicyResponseDTO responseDTO = new CouponPolicyResponseDTO(
			1L, BigDecimal.valueOf(100), BigDecimal.valueOf(10), null,
			null, "sale", true, null, null, null, null
		);
		Page<CouponPolicyResponseDTO> couponPolicyPage = new PageImpl<>(Collections.singletonList(responseDTO));
		when(couponPolicyServiceFeignClient.getAllCouponPolicies(any(Pageable.class)))
			.thenReturn(ResponseEntity.ok(couponPolicyPage));

		// When
		Page<CouponPolicyResponseDTO> result = couponPolicyServiceImpl.getAllCouponPolicies(pageable);

		// Then
		verify(couponPolicyServiceFeignClient, times(1)).getAllCouponPolicies(pageable);
		assert(result.getContent().size() == 1);
		assert(result.getContent().getFirst().equals(responseDTO));
	}
}