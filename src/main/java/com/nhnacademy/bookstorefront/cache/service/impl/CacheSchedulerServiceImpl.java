package com.nhnacademy.bookstorefront.cache.service.impl;

import com.nhnacademy.bookstorefront.cache.service.CacheSchedulerService;
import com.nhnacademy.bookstorefront.cache.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("prod")
@RequiredArgsConstructor
public class CacheSchedulerServiceImpl implements CacheSchedulerService {
    private final CacheService cacheService;

    /**
     * {@inheritDoc}
     */
    @Scheduled(cron = "0 0 * * * ?") // 1시간마다 캐시 갱신
    public void refreshMainPageCache() {
        cacheService.initMainPageCache();
        cacheService.getOrderedBooks();
        cacheService.getLikesBooks();
        cacheService.getNewestBooks();
        log.info("메인 페이지 도서 목록 캐시 업데이트 완료");
    }

}