package com.nhnacademy.bookstorefront.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.cart.dto.response.GetCartResponse;
import com.nhnacademy.bookstorefront.cart.service.CartService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {
	private final CartService cartService;

	@GetMapping("/{cartId}")
	public GetCartResponse getCart(@PathVariable Long cartId) {
		return cartService.getCart(cartId).getBody();
	}

	@PostMapping
	public ResponseEntity<Void> createCart() {
		return cartService.createCart();
	}

}
