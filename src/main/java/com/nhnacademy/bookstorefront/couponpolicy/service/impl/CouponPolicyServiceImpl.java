package com.nhnacademy.bookstorefront.couponpolicy.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyCreateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyUpdateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.response.CouponPolicyResponseDTO;
import com.nhnacademy.bookstorefront.couponpolicy.feignclient.CouponPolicyServiceFeignClient;
import com.nhnacademy.bookstorefront.couponpolicy.service.CouponPolicyService;

@Service
public class CouponPolicyServiceImpl implements CouponPolicyService {
	private final CouponPolicyServiceFeignClient couponPolicyServiceFeignClient;

	public CouponPolicyServiceImpl(CouponPolicyServiceFeignClient couponPolicyServiceFeignClient) {
		this.couponPolicyServiceFeignClient = couponPolicyServiceFeignClient;
	}


	@Override
	public CouponPolicyResponseDTO issueWelcomeCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		return couponPolicyServiceFeignClient.issueWelcomeCoupon(requestDTO).getBody();
	}
	@Override
	public CouponPolicyResponseDTO issueBirthdayCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		return couponPolicyServiceFeignClient.issueBirthdayCoupon(requestDTO).getBody();
	}

	@Override
	public CouponPolicyResponseDTO issueBookCoupon(Long bookId, CouponPolicyCreateRequestDTO requestDTO) {
		return couponPolicyServiceFeignClient.issueSpecificBookCoupon(bookId, requestDTO).getBody();
	}

	@Override
	public CouponPolicyResponseDTO issueCategoryCoupon(Long categoryId, CouponPolicyCreateRequestDTO requestDTO) {
		return couponPolicyServiceFeignClient.issueSpecificCategoryCoupon(categoryId, requestDTO).getBody();
	}



	@Override
	public CouponPolicyResponseDTO issueSaleCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		return couponPolicyServiceFeignClient.issueDiscountCoupon(requestDTO).getBody();
	}


	@Override
	public CouponPolicyResponseDTO updateCouponPolicy(Long id, CouponPolicyUpdateRequestDTO requestDTO) {
		return couponPolicyServiceFeignClient.updateCouponPolicy(id, requestDTO).getBody();
	}

	@Override
	public void deleteCouponPolicy(Long id) {
		couponPolicyServiceFeignClient.deleteCouponPolicy(id);
	}

	@Override
	public List<CouponPolicyResponseDTO> getAllCouponPolicies() {
		return couponPolicyServiceFeignClient.getAllCouponPolicies().getBody();
	}


	@Override
	public CouponPolicyResponseDTO getCouponPolicy(Long couponPolicyId) {
		return couponPolicyServiceFeignClient.getCouponPolicyById(couponPolicyId).getBody();
	}
}