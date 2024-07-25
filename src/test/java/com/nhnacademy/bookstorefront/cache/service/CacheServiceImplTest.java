package com.nhnacademy.bookstorefront.cache.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import com.nhnacademy.bookstorefront.book.service.BookService;
import com.nhnacademy.bookstorefront.cache.service.impl.CacheServiceImpl;
import com.nhnacademy.bookstorefront.category.service.CategoryService;

class CacheServiceImplTest {

	@Mock
	private CategoryService categoryService;

	@Mock
	private BookService bookService;

	@InjectMocks
	private CacheServiceImpl cacheService;

	@MockBean
	private CacheManager cacheManager;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		cacheManager = new ConcurrentMapCacheManager(
			"categoriesCache",
			"orderedBooksCache",
			"likesBooksCache",
			"newestBooksCache"
		);
	}

	@Test
	void testRefreshMainPageCache() {
		// Given
		cacheService.getOrderedBooks();
		cacheService.getLikesBooks();
		cacheService.getNewestBooks();

		// Call the method to refresh caches
		cacheService.refreshMainPageCache();

		// Verify that caches are evicted
		Cache orderedBooksCache = cacheManager.getCache("orderedBooksCache");
		Cache likesBooksCache = cacheManager.getCache("likesBooksCache");
		Cache newestBooksCache = cacheManager.getCache("newestBooksCache");

		assertThat(orderedBooksCache).isNotNull();
		assertThat(likesBooksCache).isNotNull();
		assertThat(newestBooksCache).isNotNull();

		// Check if the caches are empty after eviction
		assertThat(orderedBooksCache.get("orderedBooks")).isNull();
		assertThat(likesBooksCache.get("likesBooks")).isNull();
		assertThat(newestBooksCache.get("newestBooks")).isNull();
	}
}
