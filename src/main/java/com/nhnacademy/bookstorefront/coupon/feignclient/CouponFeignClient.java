package com.nhnacademy.bookstorefront.coupon.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.coupon.domain.dto.request.CouponCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupon.domain.dto.response.CouponResponseDTO;
import com.nhnacademy.bookstorefront.global.config.FeignClientConfig;

@FeignClient(name = "book-store-coupon", url = "http://localhost:9494", configuration = FeignClientConfig.class)
public interface CouponFeignClient {

	@PostMapping("coupons")
	ResponseEntity<CouponResponseDTO> createCoupon(@RequestBody CouponCreateRequestDTO requestDTO);

	@GetMapping("coupons")
	ResponseEntity<List<CouponResponseDTO>> getAllCoupons();

}


