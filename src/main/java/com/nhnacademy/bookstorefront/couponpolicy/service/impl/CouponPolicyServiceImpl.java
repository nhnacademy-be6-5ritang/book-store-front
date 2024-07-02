package com.nhnacademy.bookstorefront.couponpolicy.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyCreateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyUpdateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.response.CouponPolicyResponseDTO2;
import com.nhnacademy.bookstorefront.couponpolicy.feignclient.CouponPolicyServiceFeignClient;
import com.nhnacademy.bookstorefront.couponpolicy.service.CouponPolicyService;

@Service
public class CouponPolicyServiceImpl implements CouponPolicyService {
	private final CouponPolicyServiceFeignClient couponPolicyServiceFeignClient;

	public CouponPolicyServiceImpl(CouponPolicyServiceFeignClient couponPolicyServiceFeignClient) {
		this.couponPolicyServiceFeignClient = couponPolicyServiceFeignClient;
	}


	@Override
	public void issueWelcomeCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueWelcomeCoupon(requestDTO).getBody();
	}
	@Override
	public void issueBirthdayCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueBirthdayCoupon(requestDTO).getBody();
	}

	@Override
	public void issueBookCoupon(Long bookId, CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueSpecificBookCoupon(bookId, requestDTO).getBody();
	}

	@Override
	public void issueCategoryCoupon(Long categoryId, CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueSpecificCategoryCoupon(categoryId, requestDTO).getBody();
	}



	@Override
	public void issueSaleCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueDiscountCoupon(requestDTO).getBody();
	}


	@Override
	public void updateCouponPolicy(Long id, CouponPolicyUpdateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.updateCouponPolicy(id, requestDTO).getBody();
	}



	@Override
	public List<CouponPolicyResponseDTO2> getAllCouponPolicies() {
		return couponPolicyServiceFeignClient.getAllCouponPolicies().getBody();
	}

}