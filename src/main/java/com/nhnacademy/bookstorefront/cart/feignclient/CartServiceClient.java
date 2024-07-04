package com.nhnacademy.bookstorefront.cart.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nhnacademy.bookstorefront.cart.dto.CartDto;
import com.nhnacademy.bookstorefront.cart.dto.request.CreateCartRequest;

@FeignClient(name = "cart-feign-client", url = "http://localhost:8090/api/carts")
public interface CartServiceClient {

	@GetMapping("/{cartId}")
	ResponseEntity<CartDto> getCart(@PathVariable Long cartId);

	@PostMapping
	ResponseEntity<Void> createCart(CreateCartRequest request);

}
