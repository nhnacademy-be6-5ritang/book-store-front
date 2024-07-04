// package com.nhnacademy.bookstorefront.global.config;
//
// import java.util.List;
//
// import org.springframework.cache.annotation.CacheEvict;
// import org.springframework.cache.annotation.Cacheable;
// import org.springframework.cache.annotation.EnableCaching;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.bind.annotation.ModelAttribute;
//
// import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
// import com.nhnacademy.bookstorefront.category.service.CategoryService;
//
// import jakarta.annotation.PostConstruct;
// import lombok.RequiredArgsConstructor;
//
// @Configuration
// @EnableCaching
// @RequiredArgsConstructor
// public class CacheConfig {
// 	private final CategoryService categoryService;
// 	private List<GetCategoryResponse> categoriesCache; // 캐시에 저장할 변수
//
// 	@PostConstruct
// 	public void init() {
// 		// 애플리케이션이 시작될 때 초기화
// 		categoriesCache = categoryService.getCategories();
// 	}
//
// 	@ModelAttribute
// 	@Cacheable(value = "categoriesCache")
// 	public List<GetCategoryResponse> getCachedCategories() {
// 		// 캐시된 카테고리 목록 반환
// 		return categoriesCache;
// 	}
//
// 	@CacheEvict(value = "categoriesCache", allEntries = true)
// 	public void evictCache() {
// 		// 캐시 비우기
// 	}
// }
