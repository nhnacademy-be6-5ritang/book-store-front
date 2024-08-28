package com.nhnacademy.bookstorefront.cache.service;

import com.nhnacademy.bookstorefront.cache.service.impl.CacheManageServiceImpl;
import com.nhnacademy.bookstorefront.cache.service.impl.CacheServiceImpl;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.category.service.CategoryService;
import com.nhnacademy.bookstorefront.product.dto.response.GetProductSimpleResponse;
import com.nhnacademy.bookstorefront.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class CacheServiceImplTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CacheServiceImpl cacheService;

    @InjectMocks
    private CacheManageServiceImpl cacheManageService;

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
        assertThat(categories).isEmpty(); // Exception should result in empty list

        Cache cache = cacheManager.getCache("categoriesCache");
        assertThat(cache).isNotNull();
        assertThat(cache.get("categories")).isNull(); // Cache should be empty
    }

    @Test
    void testGetOrderedBooks() {
        // Arrange
        List<GetProductSimpleResponse> expectedBooks = Collections.emptyList();
        when(productService.getBestSellerBooks()).thenReturn(expectedBooks);

        // Act
        List<GetProductSimpleResponse> books = cacheService.getBestSellerBooks();

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
    void testGetBestSellerBooksWhenException() {
        // Arrange
        when(productService.getBestSellerBooks()).thenThrow(new RuntimeException("Service failure"));

        // Act
        List<GetProductSimpleResponse> books = cacheService.getBestSellerBooks();

        // Assert
        assertThat(books).isEmpty(); // Exception should result in empty list

        Cache cache = cacheManager.getCache("orderedBooksCache");
        assertThat(cache).isNotNull();
        assertThat(cache.get("orderedBooks")).isNull(); // Cache should be empty
    }

    @Test
    void testGetLikesBooks() {
        // Arrange
        List<GetProductSimpleResponse> expectedBooks = Collections.emptyList();
        when(productService.getLikesBooks()).thenReturn(expectedBooks);

        // Act
        List<GetProductSimpleResponse> books = cacheService.getLikesBooks();

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
        when(productService.getLikesBooks()).thenThrow(new RuntimeException("Service failure"));

        // Act
        List<GetProductSimpleResponse> books = cacheService.getLikesBooks();

        // Assert
        assertThat(books).isEmpty(); // Exception should result in empty list

        Cache cache = cacheManager.getCache("likesBooksCache");
        assertThat(cache).isNotNull();
        assertThat(cache.get("likesBooks")).isNull(); // Cache should be empty
    }

    @Test
    void testGetNewestBooks() {
        // Arrange
        List<GetProductSimpleResponse> expectedBooks = Collections.emptyList();
        when(productService.getNewestBooks()).thenReturn(expectedBooks);

        // Act
        List<GetProductSimpleResponse> books = cacheService.getNewestBooks();

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
        when(productService.getNewestBooks()).thenThrow(new RuntimeException("Service failure"));

        // Act
        List<GetProductSimpleResponse> books = cacheService.getNewestBooks();

        // Assert
        assertThat(books).isEmpty(); // Exception should result in empty list

        Cache cache = cacheManager.getCache("newestBooksCache");
        assertThat(cache).isNotNull();
        assertThat(cache.get("newestBooks")).isNull(); // Cache should be empty
    }
}
