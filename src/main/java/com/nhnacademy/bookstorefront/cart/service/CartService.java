package com.nhnacademy.bookstorefront.cart.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.cart.dto.response.GetCartResponse;
import com.nhnacademy.bookstorefront.cart.feignclient.CartServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartServiceClient cartServiceClient;

	public ResponseEntity<GetCartResponse> getCart() {
		return cartServiceClient.getCart();
	}

	public ResponseEntity<Void> createCart() {
		return cartServiceClient.createCart();
	}
}
