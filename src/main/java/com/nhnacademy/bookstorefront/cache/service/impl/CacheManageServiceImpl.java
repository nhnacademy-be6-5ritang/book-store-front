package com.nhnacademy.bookstorefront.cache.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.cache.service.CacheManageService;
import com.nhnacademy.bookstorefront.cache.service.CacheService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheManageServiceImpl implements CacheManageService {
	private final CacheService cacheService;

	@Scheduled(cron = "0 0 * * * ?") // 1시간마다 캐시 갱신
	public void refreshMainPageCache() {
		cacheService.initMainPageCache();
		cacheService.getOrderedBooks();
		cacheService.getLikesBooks();
		cacheService.getNewestBooks();
		log.info("메인 페이지 도서 목록 캐시 업데이트 완료");
	}

}