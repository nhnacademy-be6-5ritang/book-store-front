package com.nhnacademy.bookstorefront.userandcoupon.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.global.config.FeignClientConfig;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;

@FeignClient(name = "user-and-coupon-feign-client", url = "http://localhost:8090", configuration = FeignClientConfig.class)
public interface UserAndCouponFeignClient {

	@PostMapping("/coupons/{couponId}")
	ResponseEntity<Void> createUserAndCoupon(@PathVariable("couponId") Long couponId);



	@GetMapping("/coupons/users")
	ResponseEntity<Page<UserAndCouponResponseDTO>> getAllUsersAndCouponsByManagerPaging(@RequestParam(required = false)Long userId,@RequestParam(required = false)  String type, Pageable pageable);



	@GetMapping("/coupons/users/user")
	ResponseEntity<Page<UserAndCouponResponseDTO>> getAllUserAndCouponsByUserPaging( Pageable pageable);




	@PostMapping("/coupons/coupon/welcome")
	ResponseEntity<Void> createUserWelcomeCouponIssue(@RequestParam Long userId);


	//
	// @GetMapping("/coupons/users/{userId}")
	// ResponseEntity<Page<UserAndCouponResponseDTO>> getAllUserAndCouponsByUserPaging(@PathVariable("userId") Long userId, Pageable pageable);
}



