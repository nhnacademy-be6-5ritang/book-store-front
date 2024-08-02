package com.nhnacademy.bookstorefront.bookcart.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.bookcart.dto.request.CreateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.response.GetBookCartResponse;
import com.nhnacademy.bookstorefront.bookcart.feignclient.BookCartServiceClient;
import com.nhnacademy.bookstorefront.bookcart.service.BookCartService;
import com.nhnacademy.bookstorefront.global.util.CookieUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookCartServiceImpl implements BookCartService {
	private final BookCartServiceClient bookCartServiceClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public List<GetBookCartResponse> getBookCartsByCartId(String cartId, HttpServletResponse resp) {
		ResponseEntity<List<GetBookCartResponse>> responseEntity = bookCartServiceClient.getBookCartsByCartId(cartId);
		CookieUtil.responseCookies(responseEntity.getHeaders(), resp);
		return responseEntity.getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> createBookCart(CreateBookCartRequest request, String cartId) {
		return bookCartServiceClient.createBookCart(request, cartId);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> updateBookCart(Long bookId, UpdateBookCartRequest request, String cartId) {
		return bookCartServiceClient.updateBookCart(bookId, request, cartId);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> deleteBookCart(Long bookId, String cartId) {
		return bookCartServiceClient.deleteBookCart(bookId, cartId);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> deleteAllBookCart(String cartId) {
		return bookCartServiceClient.deleteAllBookCart(cartId);
	}
}
