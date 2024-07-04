package com.nhnacademy.bookstorefront.cart.service;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.cart.dto.request.CreateCartRequest;
import com.nhnacademy.bookstorefront.cart.feignclient.CartServiceClient;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartServiceClient cartServiceClient;

	public void createCart(HttpServletRequest request) {
		String cookieHeader = "";
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				cookieHeader += cookie.getName() + "=" + cookie.getValue() + "; ";
			}
		}

		request.getCookies();

		cartServiceClient.createCart(new CreateCartRequest(request.getCookies()));
	}
}
