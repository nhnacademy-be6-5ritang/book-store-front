package com.nhnacademy.bookstorefront.cart.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.cart.dto.response.GetCartResponse;
import com.nhnacademy.bookstorefront.cart.feignclient.CartServiceClient;
import com.nhnacademy.bookstorefront.cart.service.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	private final CartServiceClient cartServiceClient;

	public ResponseEntity<GetCartResponse> getCart(Long cartId) {
		return cartServiceClient.getCart(cartId);
	}

	public ResponseEntity<Void> createCart() {
		return cartServiceClient.createCart();
	}
}
