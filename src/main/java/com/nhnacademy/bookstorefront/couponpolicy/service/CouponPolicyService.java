package com.nhnacademy.bookstorefront.couponpolicy.service;

import java.util.List;

import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyCreateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyUpdateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.response.CouponPolicyResponseDTO;

public interface CouponPolicyService {
	void issueWelcomeCoupon(CouponPolicyCreateRequestDTO requestDTO);

	void issueBirthdayCoupon(CouponPolicyCreateRequestDTO requestDTO);

	void issueBookCoupon(Long bookId, CouponPolicyCreateRequestDTO requestDTO);

	void issueCategoryCoupon(Long categoryId, CouponPolicyCreateRequestDTO requestDTO);

	void issueSaleCoupon(CouponPolicyCreateRequestDTO requestDTO);

	List<CouponPolicyResponseDTO> getAllCouponPolicies();

	void updateCouponPolicy(Long id, CouponPolicyUpdateRequestDTO requestDTO);



}
