package com.nhnacademy.bookstorefront.cache.service;

import java.util.List;

import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;

/**
 * @author 이경헌
 * 카테고리 및 도서와 관련된 캐시 서비스를 위한 인터페이스.
 */
public interface CacheService {

	/**
	 * 캐시된 카테고리 목록을 검색합니다.
	 *
	 * @return 캐시된 카테고리를 나타내는 {@link GetCategoryResponse} 목록.
	 */
	List<GetCategoryResponse> getCachedCategories();

	/**
	 * 캐시된 베스트셀러 목록을 검색합니다.
	 *
	 * @return 캐시된 주문된 도서를 나타내는 {@link GetBookDetailResponse} 목록.
	 */
	List<GetBookDetailResponse> getOrderedBooks();

	/**
	 * 캐시된 좋아요가 많은 도서 목록을 검색합니다.
	 *
	 * @return 캐시된 좋아요가 많은 도서를 나타내는 {@link GetBookDetailResponse} 목록.
	 */
	List<GetBookDetailResponse> getLikesBooks();

	/**
	 * 캐시된 최신 도서 목록을 검색합니다.
	 *
	 * @return 캐시된 최신 도서를 나타내는 {@link GetBookDetailResponse} 목록.
	 */
	List<GetBookDetailResponse> getNewestBooks();

	/**
	 * 메인 페이지 캐시를 갱신합니다.
	 * 이 메서드는 일반적으로 정기적으로 실행되도록 스케줄링되어 캐시 데이터를 최신 상태로 유지합니다.
	 */
	void refreshMainPageCache();
}
