package com.nhnacademy.bookstorefront.book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.book.dto.request.BookUpdateRequest;
import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.CreateBookResponse;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.dto.response.UpdateBookResponse;
import com.nhnacademy.bookstorefront.book.feignclient.BookServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	private final BookServiceClient bookServiceClient;

	public GetBookDetailResponse getBook(Long bookId) {
		return bookServiceClient.getBook(bookId).getBody();
	}

	public Page<GetBookDetailResponse> findAllBooks() {
		return bookServiceClient.findAllBooks().getBody();
	}

	public Page<GetBookDetailResponse> findAllBooks(Pageable pageable) {
		return bookServiceClient.findAllBooks(pageable).getBody();
	}

	public CreateBookResponse createBook(CreateBookRequest request) {
		return bookServiceClient.createBook(request).getBody();
	}

	public UpdateBookResponse updateBookById(Long bookId, UpdateBookRequest request) {
		return bookServiceClient.updateBookById(bookId, request);
	}

	public GetBookDetailResponse updateBookByIsbn(String isbn, BookUpdateRequest request) {
		return bookServiceClient.updateBookByIsbn(isbn, request);
	}

	public void deleteBook(Long bookId) {
		bookServiceClient.deleteBook(bookId);
	}
}
