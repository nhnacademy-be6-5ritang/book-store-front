package com.nhnacademy.bookstorefront.bookcart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.bookcart.dto.request.CreateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.response.CreateBookCartResponse;
import com.nhnacademy.bookstorefront.bookcart.dto.response.GetBookCartResponse;
import com.nhnacademy.bookstorefront.bookcart.dto.response.UpdateBookCartResponse;
import com.nhnacademy.bookstorefront.bookcart.feignclient.BookCartServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookCartService {
	private final BookCartServiceClient bookCartServiceClient;

	CreateBookCartResponse createBookCart(CreateBookCartRequest request) {
		return bookCartServiceClient.createBookCart(request).getBody();
	}

	List<GetBookCartResponse> getBookCartsByUserId(Long userId) {
		return bookCartServiceClient.getBookCartsByUserId().getBody();
	}

	UpdateBookCartResponse updateBookCart(Long bookCartId, UpdateBookCartRequest request) {
		return bookCartServiceClient.updateBookCart(bookCartId, request).getBody();
	}

	void deleteBookCart(Long bookCartId) {
		bookCartServiceClient.deleteBookCart(bookCartId);
	}
}
