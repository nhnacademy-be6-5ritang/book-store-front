package com.nhnacademy.bookstorefront.coupontemplate.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.request.CouponTemplateCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.response.CouponTemplateResponseDTO;
import com.nhnacademy.bookstorefront.global.config.FeignClientConfig;

@FeignClient(name = "coupon-template-feign-client", url = "http://localhost:9494", configuration = FeignClientConfig.class)
public interface CouponTemplateFeignClient {

	@PostMapping("/coupons")
	ResponseEntity<CouponTemplateResponseDTO> createCouponTemplate(@RequestBody CouponTemplateCreateRequestDTO requestDTO);

	@GetMapping("/coupons")
	ResponseEntity<List<CouponTemplateResponseDTO>> getAllCouponTemplates();

	@GetMapping("/coupons/issue")
	ResponseEntity<Page<CouponTemplateResponseDTO>> getAllCouponTemplatePaging(Pageable pageable);

}


