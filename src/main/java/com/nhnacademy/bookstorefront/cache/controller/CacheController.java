package com.nhnacademy.bookstorefront.cache.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.cache.service.CacheService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/caches")
public class CacheController {
	private final CacheService cacheService;

	@GetMapping
	public String cacheManagePage() {
		return "cache/main-cache";
	}

	@GetMapping("/mainPage")
	public ResponseEntity<Void> refreshCacheMainPage() {
		cacheService.refreshMainPageCache();
		return ResponseEntity.ok().build();
	}
}
