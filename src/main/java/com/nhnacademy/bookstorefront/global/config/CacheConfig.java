package com.nhnacademy.bookstorefront.global.config;

import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.category.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 이경헌
 * 스프링의 캐시 추상화를 이용한 캐시 관리와 캐시 갱신을 위한 스케줄링을 설정하는 클래스입니다.
 *
 * @version 1.0
 */
@Slf4j
@Configuration
@EnableCaching
@EnableScheduling
@RequiredArgsConstructor
public class CacheConfig {
	private final CategoryService categoryService;

	/**
	 * 캐시를 관라합니다.
	 *
	 */
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("categoriesCache");
	}

	/**
	 * 고정된 간격으로 캐시를 갱신하는 스케줄된 메서드입니다.
	 * "categoriesCache" 캐시에서 모든 항목을 제거합니다.
	 */
	@Scheduled(fixedRate = 300000) // 5분마다 캐시 갱신
	@CacheEvict(value = {"categoriesCache"}, allEntries = true) // 기존 캐시 제거
	public void refreshCache() {
		log.info("Refresh cache completed.");
	}

	/**
	 * 캐시된 카테고리를 가져오거나, 캐시가 없는 경우 CategoryService에서 가져와서 결과를 캐시합니다.
	 * @return 카테고리를 나타내는 GetCategoryResponse 객체의 리스트
	 */
	@Cacheable(value = "categoriesCache")
	public List<GetCategoryResponse> getCachedCategories() {
		return categoryService.getCategories();
	}
}
