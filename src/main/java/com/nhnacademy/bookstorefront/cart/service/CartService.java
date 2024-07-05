package com.nhnacademy.bookstorefront.cart.service;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.cart.dto.request.CreateCartRequest;
import com.nhnacademy.bookstorefront.cart.dto.response.CreateCartResponse;
import com.nhnacademy.bookstorefront.cart.feignclient.CartServiceClient;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartServiceClient cartServiceClient;

	public CreateCartResponse createCart(HttpServletRequest request) {
		return cartServiceClient.createCart(new CreateCartRequest(request.getCookies())).getBody();
	}
}
