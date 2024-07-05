package com.nhnacademy.bookstorefront.bookcart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.cart.dto.response.CreateCartResponse;
import com.nhnacademy.bookstorefront.cart.service.CartService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/carts/me")
public class BookCartController {
	private final CartService cartService;

	@GetMapping
	public String getCart(Model model, @PathVariable Long cartId) {
		return "cart/get-cart";
	}

	@PostMapping
	public CreateCartResponse createCart(HttpServletRequest request) {
		return cartService.createCart(request);
	}

}
