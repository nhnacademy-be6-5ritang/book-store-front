package com.nhnacademy.bookstorefront.userandcoupon.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.global.config.FeignClientConfig;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request.UserAndCouponRequestCreateDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request.UserAndCouponRequestUpdateDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;

@FeignClient(name = "book-store-coupon", url = "http://localhost:9494", configuration = FeignClientConfig.class)
public interface UserAndCouponFeignClient {

	@PostMapping("/coupons/{couponId}")
	ResponseEntity<UserAndCouponResponseDTO> createUserAndCoupon(@PathVariable("couponId") Long couponId, @RequestBody
		UserAndCouponRequestCreateDTO createDTO);

	@PatchMapping("/coupons/users/{userEmail}")
	ResponseEntity<UserAndCouponResponseDTO> updateUserAndCoupon(@PathVariable("userEmail") String userId,
		@RequestBody UserAndCouponRequestUpdateDTO requestDTO);

	@GetMapping("/coupons/users")
	ResponseEntity<List<UserAndCouponResponseDTO>> getAllUserAndCoupons();


	@GetMapping("/coupons/users/{userEmail}")
	ResponseEntity<List<UserAndCouponResponseDTO>> getUserAndCouponById(@PathVariable("userEmail") String userId);
}



