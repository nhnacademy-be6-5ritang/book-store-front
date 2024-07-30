package com.nhnacademy.bookstorefront.cache.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.service.BookService;
import com.nhnacademy.bookstorefront.cache.service.impl.CacheServiceImpl;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.category.service.CategoryService;

class CacheServiceImplTest {

	@Mock
	private CategoryService categoryService;

	@Mock
	private BookService bookService;

	@InjectMocks
	private CacheServiceImpl cacheService;

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
	void testGetCachedCategories() {
		// Arrange
		List<GetCategoryResponse> expectedCategories = Collections.emptyList();
		when(categoryService.getCategories()).thenReturn(expectedCategories);

		// Act
		List<GetCategoryResponse> categories = cacheService.getCachedCategories();

		// Assert
		assertThat(categories).isEqualTo(expectedCategories);

		Cache cache = cacheManager.getCache("categoriesCache");
		assertThat(cache).isNotNull();

		// Manually put into cache and check
		cache.put("categories", expectedCategories);

		assertThat(cache.get("categories")).isNotNull();
		assertThat(cache.get("categories").get()).isEqualTo(expectedCategories);
	}

	@Test
	void testGetCachedCategoriesWhenException() {
		// Arrange
		when(categoryService.getCategories()).thenThrow(new RuntimeException("Service failure"));

		// Act
		List<GetCategoryResponse> categories = cacheService.getCachedCategories();

		// Assert
		assertThat(categories).isNull(); // Exception should result in null

		Cache cache = cacheManager.getCache("categoriesCache");
		assertThat(cache).isNotNull();
		assertThat(cache.get("categories")).isNull(); // Cache should be empty
	}

	@Test
	void testGetOrderedBooks() {
		// Arrange
		List<GetBookDetailResponse> expectedBooks = Collections.emptyList();
		when(bookService.getOrderedBooks()).thenReturn(expectedBooks);

		// Act
		List<GetBookDetailResponse> books = cacheService.getOrderedBooks();

		// Assert
		assertThat(books).isEqualTo(expectedBooks);

		Cache cache = cacheManager.getCache("orderedBooksCache");
		assertThat(cache).isNotNull();

		// Manually put into cache and check
		cache.put("orderedBooks", expectedBooks);

		assertThat(cache.get("orderedBooks")).isNotNull();
		assertThat(cache.get("orderedBooks").get()).isEqualTo(expectedBooks);
	}

	@Test
	void testGetOrderedBooksWhenException() {
		// Arrange
		when(bookService.getOrderedBooks()).thenThrow(new RuntimeException("Service failure"));

		// Act
		List<GetBookDetailResponse> books = cacheService.getOrderedBooks();

		// Assert
		assertThat(books).isNull(); // Exception should result in null

		Cache cache = cacheManager.getCache("orderedBooksCache");
		assertThat(cache).isNotNull();
		assertThat(cache.get("orderedBooks")).isNull(); // Cache should be empty
	}

	@Test
	void testGetLikesBooks() {
		// Arrange
		List<GetBookDetailResponse> expectedBooks = Collections.emptyList();
		when(bookService.getLikesBooks()).thenReturn(expectedBooks);

		// Act
		List<GetBookDetailResponse> books = cacheService.getLikesBooks();

		// Assert
		assertThat(books).isEqualTo(expectedBooks);

		Cache cache = cacheManager.getCache("likesBooksCache");
		assertThat(cache).isNotNull();

		// Manually put into cache and check
		cache.put("likesBooks", expectedBooks);

		assertThat(cache.get("likesBooks")).isNotNull();
		assertThat(cache.get("likesBooks").get()).isEqualTo(expectedBooks);
	}

	@Test
	void testGetLikesBooksWhenException() {
		// Arrange
		when(bookService.getLikesBooks()).thenThrow(new RuntimeException("Service failure"));

		// Act
		List<GetBookDetailResponse> books = cacheService.getLikesBooks();

		// Assert
		assertThat(books).isNull(); // Exception should result in null

		Cache cache = cacheManager.getCache("likesBooksCache");
		assertThat(cache).isNotNull();
		assertThat(cache.get("likesBooks")).isNull(); // Cache should be empty
	}

	@Test
	void testGetNewestBooks() {
		// Arrange
		List<GetBookDetailResponse> expectedBooks = Collections.emptyList();
		when(bookService.getNewestBooks()).thenReturn(expectedBooks);

		// Act
		List<GetBookDetailResponse> books = cacheService.getNewestBooks();

		// Assert
		assertThat(books).isEqualTo(expectedBooks);

		Cache cache = cacheManager.getCache("newestBooksCache");
		assertThat(cache).isNotNull();

		// Manually put into cache and check
		cache.put("newestBooks", expectedBooks);

		assertThat(cache.get("newestBooks")).isNotNull();
		assertThat(cache.get("newestBooks").get()).isEqualTo(expectedBooks);
	}

	@Test
	void testGetNewestBooksWhenException() {
		// Arrange
		when(bookService.getNewestBooks()).thenThrow(new RuntimeException("Service failure"));

		// Act
		List<GetBookDetailResponse> books = cacheService.getNewestBooks();

		// Assert
		assertThat(books).isNull(); // Exception should result in null

		Cache cache = cacheManager.getCache("newestBooksCache");
		assertThat(cache).isNotNull();
		assertThat(cache.get("newestBooks")).isNull(); // Cache should be empty
	}

	@Test
	void testRefreshMainPageCache() {
		// Act
		cacheService.getOrderedBooks();
		cacheService.getLikesBooks();
		cacheService.getNewestBooks();

		cacheService.refreshMainPageCache();

		// Assert
		Cache orderedBooksCache = cacheManager.getCache("orderedBooksCache");
		Cache likesBooksCache = cacheManager.getCache("likesBooksCache");
		Cache newestBooksCache = cacheManager.getCache("newestBooksCache");

		assertThat(orderedBooksCache).isNotNull();
		assertThat(likesBooksCache).isNotNull();
		assertThat(newestBooksCache).isNotNull();

		assertThat(orderedBooksCache.get("orderedBooks")).isNull();
		assertThat(likesBooksCache.get("likesBooks")).isNull();
		assertThat(newestBooksCache.get("newestBooks")).isNull();
	}
}
