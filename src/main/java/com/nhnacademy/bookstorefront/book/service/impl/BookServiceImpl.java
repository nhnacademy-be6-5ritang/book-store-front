package com.nhnacademy.bookstorefront.book.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.book.dto.response.BookDetailResponse;
import com.nhnacademy.bookstorefront.book.dto.response.BookListResponse;
import com.nhnacademy.bookstorefront.book.feignclient.BookServiceClient;
import com.nhnacademy.bookstorefront.book.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	private final BookServiceClient bookServiceClient;

	@Override
	public BookDetailResponse findBookById(Long bookId) {
		return bookServiceClient.getBook(bookId);
	}

	@Override
	public List<BookListResponse> findAllBooks() {
		return bookServiceClient.findAllBooks();
	}

	@Override
	public BookDetailResponse findBookByIsbn(String isbn) {
		return null;
	}
}
