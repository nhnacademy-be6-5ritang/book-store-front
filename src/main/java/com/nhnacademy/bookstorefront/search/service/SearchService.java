package com.nhnacademy.bookstorefront.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.search.dto.BookSearchResponse;

/**
 * @author 김태환
 * 검색 서비스의 인터페이스입니다.
 */
public interface SearchService {

	/**
	 * 주어진 검색어(query)를 사용하여 책을 검색합니다.
	 *
	 * @param query 검색어
	 * @param pageable 페이지 정보
	 * @return 검색 결과를 포함한 페이지 객체
	 */
	Page<BookSearchResponse> searchBooks(String query, Pageable pageable);

	/**
	 * 주어진 검색어(query)를 사용하여 저자를 검색합니다.
	 *
	 * @param query 검색어
	 * @param pageable 페이지 정보
	 * @return 검색 결과를 포함한 페이지 객체
	 */
	Page<BookSearchResponse> searchAuthors(String query, Pageable pageable);

	/**
	 * 주어진 검색어(query)를 사용하여 출판사를 검색합니다.
	 *
	 * @param query 검색어
	 * @param pageable 페이지 정보
	 * @return 검색 결과를 포함한 페이지 객체
	 */
	Page<BookSearchResponse> searchPublishers(String query, Pageable pageable);

	/**
	 * 주어진 검색어(query)를 사용하여 태그를 기반으로 책을 검색합니다.
	 *
	 * @param query 검색어
	 * @param pageable 페이지 정보
	 * @return 검색 결과를 포함한 페이지 객체
	 */
	Page<BookSearchResponse> searchBooksByTag(String query, Pageable pageable);
}
