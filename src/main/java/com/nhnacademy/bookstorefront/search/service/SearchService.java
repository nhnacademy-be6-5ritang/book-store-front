package com.nhnacademy.bookstorefront.search.service;

import com.nhnacademy.bookstorefront.search.dto.BookSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {
	Page<BookSearchResponse> searchBooks(String query, Pageable pageable);
	Page<BookSearchResponse> searchAuthors(String query, Pageable pageable);
	Page<BookSearchResponse> searchPublishers(String query, Pageable pageable);
	Page<BookSearchResponse> searchBooksByTag(String query, Pageable pageable);
}