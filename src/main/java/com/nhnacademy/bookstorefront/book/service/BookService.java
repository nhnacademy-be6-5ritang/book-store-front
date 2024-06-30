package com.nhnacademy.bookstorefront.book.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookListResponse;
import com.nhnacademy.bookstorefront.book.dto.response.CreateBookResponse;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.feignclient.BookServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	private final BookServiceClient bookServiceClient;

	public GetBookDetailResponse getBook(Long bookId) {
		return bookServiceClient.getBook(bookId).getBody();
	}

	public List<BookListResponse> findAllBooks() {
		return bookServiceClient.findAllBooks();
	}

	public CreateBookResponse createBook(CreateBookRequest request) {
		return bookServiceClient.createBook(request).getBody();
	}

	public void deleteBook(Long bookId) {
		bookServiceClient.deleteBook(bookId);
	}
}
