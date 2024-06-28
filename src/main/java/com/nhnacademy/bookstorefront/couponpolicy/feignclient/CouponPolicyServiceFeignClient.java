package com.nhnacademy.bookstorefront.couponpolicy.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

		@PostMapping("/coupons/policies/books/{bookId}")
		ResponseEntity<CouponPolicyResponseDTO> issueSpecificBookCoupon(@PathVariable("bookId") Long bookId, @RequestBody CouponPolicyCreateRequestDTO requestDTO);

		@PostMapping("/coupons/policies/categories/{categoryId}")
		ResponseEntity<CouponPolicyResponseDTO> issueSpecificCategoryCoupon(@PathVariable("categoryId") Long categoryId, @RequestBody CouponPolicyCreateRequestDTO requestDTO);

		@PostMapping("/coupons/policies/sale")
		ResponseEntity<CouponPolicyResponseDTO> issueDiscountCoupon(@RequestBody CouponPolicyCreateRequestDTO requestDTO);

		@GetMapping("/coupons/policies")
		ResponseEntity<List<CouponPolicyResponseDTO>> getAllCouponPolicies();

		@GetMapping("/coupons/policies/{couponPolicyId}")
		ResponseEntity<CouponPolicyResponseDTO> getCouponPolicyById(@PathVariable("couponPolicyId") Long couponPolicyId);

		@PatchMapping("/coupons/policies/{couponPolicyId}")
		ResponseEntity<CouponPolicyResponseDTO> updateCouponPolicy(@PathVariable("couponPolicyId") Long couponPolicyId, @RequestBody CouponPolicyUpdateRequestDTO requestDTO);

		@DeleteMapping("/coupons/policies/{couponPolicyId}")
		ResponseEntity<Void> deleteCouponPolicy(@PathVariable("couponPolicyId") Long couponPolicyId);
	}


