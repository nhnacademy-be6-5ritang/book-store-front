package com.nhnacademy.bookstorefront.couponpolicy.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyCreateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyUpdateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.response.CouponPolicyResponseDTO;
import com.nhnacademy.bookstorefront.global.config.FeignClientConfig;

@FeignClient(name = "coupon-feign-client", url = "http://localhost:8090", configuration = FeignClientConfig.class)
public interface CouponPolicyServiceFeignClient {



		@PostMapping("/coupons/policies/welcome")
		ResponseEntity<CouponPolicyResponseDTO> issueWelcomeCoupon(@RequestBody CouponPolicyCreateRequestDTO requestDTO);

		@PostMapping("/coupons/policies/birthday")
		ResponseEntity<CouponPolicyResponseDTO> issueBirthdayCoupon(@RequestBody CouponPolicyCreateRequestDTO requestDTO);

		@PostMapping("/coupons/policies/books")
		ResponseEntity<CouponPolicyResponseDTO> issueSpecificBookCoupon(@RequestBody CouponPolicyCreateRequestDTO requestDTO);

		@PostMapping("/coupons/policies/categories")
		ResponseEntity<CouponPolicyResponseDTO> issueSpecificCategoryCoupon(@RequestBody CouponPolicyCreateRequestDTO requestDTO);

		@PostMapping("/coupons/policies/sale")
		ResponseEntity<CouponPolicyResponseDTO> issueDiscountCoupon(@RequestBody CouponPolicyCreateRequestDTO requestDTO);

		@GetMapping("/coupons/policies")
		ResponseEntity<Page<CouponPolicyResponseDTO>> getAllCouponPolicies(Pageable pageable);



		@PatchMapping("/coupons/policies/{couponPolicyId}")
		ResponseEntity<Void> updateCouponPolicy(@PathVariable("couponPolicyId") Long couponPolicyId, @RequestBody CouponPolicyUpdateRequestDTO requestDTO);


	}


