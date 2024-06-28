package com.nhnacademy.bookstorefront.book.service;

import java.util.List;

import com.nhnacademy.bookstorefront.book.dto.response.BookDetailResponse;
import com.nhnacademy.bookstorefront.book.dto.response.BookListResponse;

public interface BookService {
	BookDetailResponse findBookById(Long bookId);

	List<BookListResponse> findAllBooks();

	BookDetailResponse findBookByIsbn(String isbn);

	// BookDetailResponse getBookDetailResponse(Book book);
}
