package com.nhnacademy.bookstorefront.global.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "swaggerClient", url = "http://localhost:8090")
public interface SwaggerApiClient {
	@GetMapping("/coupons/api")
	ResponseEntity<String> getSwaggerJson();

	@GetMapping("/back/api")
	ResponseEntity<String> getBackSwaggerJson();
}