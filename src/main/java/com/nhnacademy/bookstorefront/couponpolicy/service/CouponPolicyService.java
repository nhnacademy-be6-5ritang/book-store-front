package com.nhnacademy.bookstorefront.couponpolicy.service;

import java.util.List;

import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyCreateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyUpdateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.response.CouponPolicyResponseDTO;

public interface CouponPolicyService {
	CouponPolicyResponseDTO issueWelcomeCoupon(CouponPolicyCreateRequestDTO requestDTO);

	CouponPolicyResponseDTO issueBirthdayCoupon(CouponPolicyCreateRequestDTO requestDTO);

	CouponPolicyResponseDTO issueBookCoupon(Long bookId, CouponPolicyCreateRequestDTO requestDTO);

	CouponPolicyResponseDTO issueCategoryCoupon(Long categoryId, CouponPolicyCreateRequestDTO requestDTO);

	CouponPolicyResponseDTO issueSaleCoupon(CouponPolicyCreateRequestDTO requestDTO);

	List<CouponPolicyResponseDTO> getAllCouponPolicies();

	CouponPolicyResponseDTO updateCouponPolicy(Long id, CouponPolicyUpdateRequestDTO requestDTO);

	void deleteCouponPolicy(Long id);

	CouponPolicyResponseDTO getCouponPolicy(Long couponPolicyId);

}
