package com.nhnacademy.bookstorefront.cache.service.impl;

import com.nhnacademy.bookstorefront.cache.service.CacheService;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.category.service.CategoryService;
import com.nhnacademy.bookstorefront.product.dto.response.GetProductSimpleResponse;
import com.nhnacademy.bookstorefront.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {
    private final CategoryService categoryService;
    private final ProductService productService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(cacheNames = "categoriesCache", key = "'categories'", unless = "#result == null or #result.isEmpty()")
    public List<GetCategoryResponse> getCachedCategories() {
        try {
            return categoryService.getCategories();
        } catch (Exception e) {
            log.warn("카테고리 목록 캐싱 실패: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(cacheNames = "bestSellerBooksCache", key = "'bestSellerBooks'", unless = "#result.isEmpty()")
    public List<GetProductSimpleResponse> getBestSellerBooks() {
        try {
            return productService.getBestSellerBooks();
        } catch (Exception e) {
            log.warn("최다 주문 도서 목록 캐싱 실패: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(cacheNames = "likesBooksCache", key = "'likesBooks'", unless = "#result.isEmpty()")
    public List<GetProductSimpleResponse> getLikesBooks() {
        try {
            return productService.getLikesBooks();
        } catch (Exception e) {
            log.warn("최다 좋아요 도서 목록 캐싱 실패: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(cacheNames = "newestBooksCache", key = "'newestBooks'", unless = "#result.isEmpty()")
    public List<GetProductSimpleResponse> getNewestBooks() {
        try {
            return productService.getNewestBooks();
        } catch (Exception e) {
            log.warn("최신 도서 목록 캐싱 실패: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(cacheNames = {"bestSellerBooksCache", "likesBooksCache", "newestBooksCache"}, allEntries = true)
    public void initMainPageCache() {
        log.info("메인 페이지 도서 목록 캐시 초기화 완료");
    }
}