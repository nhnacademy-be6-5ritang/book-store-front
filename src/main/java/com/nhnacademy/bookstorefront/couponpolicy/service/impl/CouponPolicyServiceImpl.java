package com.nhnacademy.bookstorefront.couponpolicy.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyCreateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyUpdateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.response.CouponPolicyResponseDTO;
import com.nhnacademy.bookstorefront.couponpolicy.feignclient.CouponPolicyServiceFeignClient;
import com.nhnacademy.bookstorefront.couponpolicy.service.CouponPolicyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponPolicyServiceImpl implements CouponPolicyService {
	private final CouponPolicyServiceFeignClient couponPolicyServiceFeignClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void issueWelcomeCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueWelcomeCoupon(requestDTO);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void issueBirthdayCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueBirthdayCoupon(requestDTO);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void issueBookCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueSpecificBookCoupon(requestDTO);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void issueCategoryCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueSpecificCategoryCoupon(requestDTO);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void issueSaleCoupon(CouponPolicyCreateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.issueDiscountCoupon(requestDTO);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void updateCouponPolicy(Long id, CouponPolicyUpdateRequestDTO requestDTO) {
		couponPolicyServiceFeignClient.updateCouponPolicy(id, requestDTO);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<CouponPolicyResponseDTO> getAllCouponPolicies(Pageable pageable) {
		return couponPolicyServiceFeignClient.getAllCouponPolicies(pageable).getBody();
	}

}
