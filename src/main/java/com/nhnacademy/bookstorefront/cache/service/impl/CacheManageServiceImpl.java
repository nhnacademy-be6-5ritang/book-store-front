package com.nhnacademy.bookstorefront.cache.service.impl;

import com.nhnacademy.bookstorefront.cache.service.CacheManageService;
import com.nhnacademy.bookstorefront.cache.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheManageServiceImpl implements CacheManageService {
    private final CacheService cacheService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void refreshMainPageCache() {
        cacheService.initMainPageCache();
        cacheService.getBestSellerBooks();
        cacheService.getLikesBooks();
        cacheService.getNewestBooks();
        log.info("메인 페이지 도서 목록 캐시 업데이트 완료");
    }

}