package com.nhnacademy.bookstorefront.book.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookSearchResult;
import com.nhnacademy.bookstorefront.book.dto.response.CreateBookResponse;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.dto.response.UpdateBookResponse;
import com.nhnacademy.bookstorefront.book.feignclient.BookServiceClient;
import com.nhnacademy.bookstorefront.book.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	private final BookServiceClient bookServiceClient;

	@Override
	public GetBookDetailResponse getBook(Long bookId) {
		return bookServiceClient.getBook(bookId).getBody();
	}

	@Override
	public List<GetBookDetailResponse> findAllBooks() {
		return bookServiceClient.findAllBooks().getBody();
	}

	@Override
	public Page<GetBookDetailResponse> findAllBooks(Pageable pageable) {
		return bookServiceClient.findAllBooks(pageable).getBody();
	}

	@Override
	public CreateBookResponse createBook(CreateBookRequest request) {
		return bookServiceClient.createBook(request).getBody();
	}

	@Override
	public UpdateBookResponse updateBookById(Long bookId, UpdateBookRequest request) {
		return bookServiceClient.updateBookById(bookId, request).getBody();
	}

	@Override
	public ResponseEntity<String> fetchAndSaveBooks(Long count) {
		return bookServiceClient.fetchAndSaveBooks(count);
	}

	@Override
	public ResponseEntity<String> fetchAndSaveBook(String isbn) {
		return bookServiceClient.fetchAndSaveBook(isbn);
	}

	@Override
	public void deleteBook(Long bookId) {
		bookServiceClient.deleteBook(bookId);
	}

	public void updateQuantity(Long bookId, int quantity) {
		bookServiceClient.updateQuantity(bookId, quantity);
	}



	@Override
	public List<BookSearchResult> searchBooks(String query) {

		return bookServiceClient.searchBooks(query).getBody();
	}
}
