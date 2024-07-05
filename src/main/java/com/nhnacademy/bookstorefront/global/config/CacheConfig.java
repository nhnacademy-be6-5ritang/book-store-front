package com.nhnacademy.bookstorefront.global.config;

import java.util.Collections;
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

import feign.FeignException;
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
	 * 카테고리 목록을 캐시에서 가져오거나, 캐시에 없으면 새로 가져와서 캐시에 저장합니다.
	 *
	 * <p>Feign 클라이언트를 통해 카테고리 목록을 가져오며, 만약 가져오는 도중 예외가 발생하면 빈 리스트를 반환합니다.</p>
	 *
	 * @return 카테고리 목록 또는 예외 발생 시 빈 리스트
	 */
	@Cacheable(value = "categoriesCache")
	public List<GetCategoryResponse> getCachedCategories() {
		try {
			return categoryService.getCategories();
		} catch (FeignException e) {
			log.warn("카테고리 목록 가져오기 실패: {}", e.getMessage());
			return Collections.emptyList();
		}
	}
}
