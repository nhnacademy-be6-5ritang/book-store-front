package com.nhnacademy.bookstorefront.cart.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nhnacademy.bookstorefront.cart.dto.response.GetCartResponse;

@FeignClient(name = "cart-feign-client", url = "http://localhost:8090/api/carts")

public interface CartServiceClient {

	@GetMapping
	ResponseEntity<GetCartResponse> getCart();

	@PostMapping
	ResponseEntity<Void> createCart();

}
