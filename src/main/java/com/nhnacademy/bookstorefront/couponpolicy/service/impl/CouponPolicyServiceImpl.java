package com.nhnacademy.bookstorefront.couponpolicy.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public void issueWelcomeCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueWelcomeCoupon(requestDTO);
	}
	@Override
	public void issueBirthdayCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueBirthdayCoupon(requestDTO);
	}

	@Override
	public void issueBookCoupon(Long bookId, CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueSpecificBookCoupon(bookId, requestDTO);
	}

	@Override
	public void issueCategoryCoupon(Long categoryId, CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueSpecificCategoryCoupon(categoryId, requestDTO);
	}



	@Override
	public void issueSaleCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueDiscountCoupon(requestDTO);
	}


	@Override
	public void updateCouponPolicy(Long id, CouponPolicyUpdateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.updateCouponPolicy(id, requestDTO);
	}



	@Override
	public Page<CouponPolicyResponseDTO> getAllCouponPolicies(Pageable pageable) {
		return couponPolicyServiceFeignClient.getAllCouponPolicies(pageable).getBody();
	}

}