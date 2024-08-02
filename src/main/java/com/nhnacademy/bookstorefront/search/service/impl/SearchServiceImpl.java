package com.nhnacademy.bookstorefront.search.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.search.dto.BookSearchResponse;
import com.nhnacademy.bookstorefront.search.feignclient.SearchServiceClient;
import com.nhnacademy.bookstorefront.search.service.SearchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
	private final SearchServiceClient searchServiceClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<BookSearchResponse> searchBooks(String query, Pageable pageable) {
		return searchServiceClient.searchBooks(query, pageable).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<BookSearchResponse> searchAuthors(String query, Pageable pageable) {
		return searchServiceClient.searchAuthors(query, pageable).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<BookSearchResponse> searchPublishers(String query, Pageable pageable) {
		return searchServiceClient.searchPublishers(query, pageable).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<BookSearchResponse> searchBooksByTag(String query, Pageable pageable) {
		return searchServiceClient.searchBooksByTag(query, pageable).getBody();
	}
}
