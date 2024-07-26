package com.nhnacademy.bookstorefront.cache.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.service.BookService;
import com.nhnacademy.bookstorefront.cache.service.CacheService;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.category.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {
	private final CategoryService categoryService;
	private final BookService bookService;

	@Cacheable(cacheNames = "categoriesCache", key = "'categories'")
	public List<GetCategoryResponse> getCachedCategories() {
		try {
			return categoryService.getCategories();
		} catch (Exception e) {
			log.warn("카테고리 목록 캐싱 실패: {}", e.getMessage());
			return null;
		}
	}

	@Cacheable(cacheNames = "orderedBooksCache", key = "'orderedBooks'")
	public List<GetBookDetailResponse> getOrderedBooks() {
		try {
			return bookService.getOrderedBooks();
		} catch (Exception e) {
			log.warn("최다 주문 도서 목록 캐싱 실패: {}", e.getMessage());
			return null;
		}
	}

	@Cacheable(cacheNames = "likesBooksCache", key = "'likesBooks'")
	public List<GetBookDetailResponse> getLikesBooks() {
		try {
			return bookService.getLikesBooks();
		} catch (Exception e) {
			log.warn("최다 좋아요 도서 목록 캐싱 실패: {}", e.getMessage());
			return null;
		}

	}

	@Cacheable(cacheNames = "newestBooksCache", key = "'newestBooks'")
	public List<GetBookDetailResponse> getNewestBooks() {
		try {
			return bookService.getNewestBooks();
		} catch (Exception e) {
			log.warn("최신 도서 목록 캐싱 실패: {}", e.getMessage());
			return null;
		}
	}

	@Scheduled(cron = "0 0 * * * ?") // 매 시간마다 캐시 갱신
	@CacheEvict(cacheNames = {"orderedBooksCache", "likesBooksCache", "newestBooksCache"}, allEntries = true)
	public void refreshMainPageCache() {
		log.info("메인 페이지 도서 목록 캐싱 초기화");
	}
}