package com.nhnacademy.bookstorefront.bookcart.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.bookcart.dto.request.CreateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.response.GetBookCartResponse;
import com.nhnacademy.bookstorefront.bookcart.feignclient.BookCartServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookCartService {
	private final BookCartServiceClient bookCartServiceClient;

	public List<GetBookCartResponse> getBookCartsByCartId() {
		return bookCartServiceClient.getBookCartsByCartId().getBody();
	}

	public ResponseEntity<Void> createBookCart(CreateBookCartRequest request) {
		return bookCartServiceClient.createBookCart(request);
	}

	public ResponseEntity<Void> updateBookCart(Long bookCartId, UpdateBookCartRequest request) {
		return bookCartServiceClient.updateBookCart(bookCartId, request);
	}

	public void deleteBookCart(Long bookCartId) {
		bookCartServiceClient.deleteBookCart(bookCartId);
	}
}
