package com.nhnacademy.bookstorefront.couponpolicy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyCreateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyUpdateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.response.CouponPolicyResponseDTO;

public interface CouponPolicyService {
	void issueWelcomeCoupon(CouponPolicyCreateRequestDTO requestDTO);

	void issueBirthdayCoupon(CouponPolicyCreateRequestDTO requestDTO);

	void issueBookCoupon(Long bookId, CouponPolicyCreateRequestDTO requestDTO);

	void issueCategoryCoupon(Long categoryId, CouponPolicyCreateRequestDTO requestDTO);

	void issueSaleCoupon(CouponPolicyCreateRequestDTO requestDTO);

	Page<CouponPolicyResponseDTO> getAllCouponPolicies(Pageable pageable);

	void updateCouponPolicy(Long id, CouponPolicyUpdateRequestDTO requestDTO);



}
