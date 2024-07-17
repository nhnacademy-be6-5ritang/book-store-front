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

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author 이경헌
 * 현재 사용자의 도서 장바구니를 관리하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/carts/me")
public class BookCartController {
	private final BookCartService bookCartService;

	/**
	 * 현재 사용자의 도서 장바구니 목록을 조회합니다.
	 *
	 * @param model  View에 전달할 데이터를 담는 Model 객체
	 * @param cartId 쿠키에서 가져온 장바구니 ID
	 * @param resp   HttpServletResponse 객체
	 * @return 도서 장바구니 목록을 보여주는 View 이름 ("cart/list-cart")
	 */
	@GetMapping
	public String getBookCarts(Model model, @CookieValue(name = "cartId", required = false) String cartId,
		HttpServletResponse resp) {
		model.addAttribute("bookCarts", bookCartService.getBookCartsByCartId(cartId, resp));
		return "cart/list-cart";
	}

	/**
	 * 새로운 도서 장바구니를 생성합니다.
	 *
	 * @param request 새로 생성할 도서 장바구니 요청 정보
	 * @param cartId  쿠키에서 가져온 장바구니 ID
	 * @return HTTP 상태 코드와 함께 응답을 반환합니다.
	 */
	@PostMapping
	public ResponseEntity<Void> createBookCart(@Valid @RequestBody CreateBookCartRequest request,
		@CookieValue(name = "cartId", required = false) String cartId) {
		return bookCartService.createBookCart(request, cartId);
	}

	/**
	 * 주어진 도서 장바구니에서 특정 도서의 수량을 업데이트합니다.
	 *
	 * @param bookId  업데이트할 도서의 ID
	 * @param request 업데이트할 도서 장바구니 요청 정보
	 * @param cartId  쿠키에서 가져온 장바구니 ID
	 * @return HTTP 상태 코드와 함께 응답을 반환합니다.
	 */
	@PutMapping("/{bookId}")
	public ResponseEntity<Void> updateBookCart(@PathVariable Long bookId,
		@RequestBody UpdateBookCartRequest request, @CookieValue(name = "cartId", required = false) String cartId) {
		return bookCartService.updateBookCart(bookId, request, cartId);
	}

	/**
	 * 주어진 도서 장바구니에서 특정 도서를 제거합니다.
	 *
	 * @param bookId 업데이트할 도서의 ID
	 * @param cartId 쿠키에서 가져온 장바구니 ID
	 * @return 도서 장바구니 목록을 보여주는 View로 리다이렉트합니다. ("/api/carts/me")
	 */
	@DeleteMapping("/{bookId}")
	public String deleteBookCart(@PathVariable Long bookId,
		@CookieValue(name = "cartId", required = false) String cartId) {
		bookCartService.deleteBookCart(bookId, cartId);
		return "redirect:/api/carts/me";
	}
}
