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

	public List<GetBookCartResponse> getBookCartsByCartId(Long cartId, HttpServletResponse resp) {
		ResponseEntity<List<GetBookCartResponse>> responseEntity = bookCartServiceClient.getBookCartsByCartId(cartId);
		CookieUtil.responseCookies(responseEntity.getHeaders(), resp);

		return responseEntity.getBody();
	}

	public ResponseEntity<Void> createBookCart(CreateBookCartRequest request, Long cartId) {
		return bookCartServiceClient.createBookCart(request, cartId);
	}

	public ResponseEntity<Void> updateBookCart(Long bookCartId, UpdateBookCartRequest request, Long cartId) {
		return bookCartServiceClient.updateBookCart(bookCartId, request, cartId);
	}

	public void deleteBookCart(Long bookCartId, Long cartId) {
		bookCartServiceClient.deleteBookCart(bookCartId, cartId);
	}
}
