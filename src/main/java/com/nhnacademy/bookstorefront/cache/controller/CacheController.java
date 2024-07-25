package com.nhnacademy.bookstorefront.cache.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.cache.service.CacheService;

import lombok.RequiredArgsConstructor;

/**
 * @author 이경헌
 * 캐시 관리와 관련된 요청을 처리하는 컨트롤러 클래스입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/caches")
public class CacheController {
	private final CacheService cacheService;

	/**
	 * 캐시 관리 페이지를 반환합니다.
	 *
	 * @return 캐시 관리 페이지의 뷰
	 */
	@GetMapping
	public String cacheManagePage() {
		return "cache/main-cache";
	}

	/**
	 * 메인 페이지의 캐시를 새로고침합니다.
	 *
	 * @return HTTP 200 OK 상태 코드를 담고 있는 {@link ResponseEntity}
	 */
	@GetMapping("/mainPage")
	public ResponseEntity<Void> refreshCacheMainPage() {
		cacheService.refreshMainPageCache();
		return ResponseEntity.ok().build();
	}
}
