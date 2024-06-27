package com.nhnacademy.bookstorefront.order.service;


import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.order.dto.response.GetBookResponse;
import com.nhnacademy.bookstorefront.order.feginclient.OrderServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	private final OrderServiceClient orderServiceClient;

	public GetBookResponse findBookById(Long bookId) {
		return orderServiceClient.getBook(bookId).getBody();
	}

}