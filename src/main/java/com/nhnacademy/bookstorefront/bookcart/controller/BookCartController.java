package com.nhnacademy.bookstorefront.bookcart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.bookcart.dto.request.CreateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.service.BookCartService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/carts/me")
public class BookCartController {
	private final BookCartService bookCartService;

	@GetMapping
	public String getBookCarts(Model model, @CookieValue(name = "cartId", required = false) Long cartId) {
		model.addAttribute("bookCarts", bookCartService.getBookCartsByCartId(cartId));
		return "cart/list-cart";
	}

	@PostMapping
	public ResponseEntity<Void> createBookCart(@RequestBody CreateBookCartRequest request,
		@CookieValue(name = "cartId", required = false) Long cartId) {
		return bookCartService.createBookCart(request, cartId);
	}

	@PutMapping("/{bookCartId}")
	public ResponseEntity<Void> updateBookCart(@PathVariable Long bookCartId,
		@RequestBody UpdateBookCartRequest request, @CookieValue(name = "cartId", required = false) Long cartId) {
		return bookCartService.updateBookCart(bookCartId, request, cartId);
	}

	@DeleteMapping("/{bookCartId}")
	public String deleteBookCart(@PathVariable Long bookCartId,
		@CookieValue(name = "cartId", required = false) Long cartId) {
		bookCartService.deleteBookCart(bookCartId, cartId);
		return "redirect:/api/carts/me";
	}

}
