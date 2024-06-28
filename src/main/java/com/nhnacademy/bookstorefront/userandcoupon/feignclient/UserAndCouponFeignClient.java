package com.nhnacademy.bookstorefront.userandcoupon.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.global.config.FeignClientConfig;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request.UserAndCouponRequestCreateDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request.UserAndCouponRequestUpdateDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;

@FeignClient(name = "user-and-coupon-feign-client", url = "http://localhost:8090/back", configuration = FeignClientConfig.class)
public interface UserAndCouponFeignClient {

	@PostMapping("/coupons/{couponTemplateId}")
	ResponseEntity<UserAndCouponResponseDTO> createUserAndCoupon(@PathVariable("couponTemplateId") Long couponId, @RequestBody
		UserAndCouponRequestCreateDTO createDTO);

	@PatchMapping("/coupons/users/{userId}")
	ResponseEntity<UserAndCouponResponseDTO> updateUserAndCoupon(@PathVariable("userId") Long userId,
		@RequestBody UserAndCouponRequestUpdateDTO requestDTO);
	//
	//
	// @GetMapping("/coupons/users")
	// ResponseEntity<Page<UserAndCouponResponseDTO>> getAllUserAndCouponPaging(Pageable pageable);


	@GetMapping("/coupons/users")
	ResponseEntity<Page<UserAndCouponResponseDTO>> getAllUserAndCouponPaging(@RequestParam(required = false)Long userId,@RequestParam(required = false)  String type, Pageable pageable);



	@GetMapping("/coupons/users/{userId}")
	ResponseEntity<Page<UserAndCouponResponseDTO>> getUserAndCouponByIdPaging(@PathVariable("userId") Long userId, Pageable pageable);
}



