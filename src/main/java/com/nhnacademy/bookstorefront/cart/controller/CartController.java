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

/**
 * @author 이경헌
 * 사용자의 장바구니를 관리하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {
	private final CartService cartService;

	/**
	 * 주어진 장바구니 ID에 해당하는 장바구니 정보를 조회합니다.
	 *
	 * @param cartId 조회할 장바구니의 ID
	 * @return 조회된 장바구니 정보 (GetCartResponse)
	 */
	@GetMapping("/{cartId}")
	public GetCartResponse getCart(@PathVariable String cartId) {
		return cartService.getCart(cartId).getBody();
	}

	/**
	 * 새로운 장바구니를 생성합니다.
	 *
	 * @return HTTP 상태 코드와 함께 응답을 반환합니다.
	 */
	@PostMapping
	public ResponseEntity<Void> createCart() {
		return cartService.createCart();
	}

}
