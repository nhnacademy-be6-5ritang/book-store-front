package com.nhnacademy.bookstorefront.cache.service;

/**
 * @author 이경헌
 * 캐시 자원을 관리 위한 서비스를 위한 인터페이스.
 */
public interface CacheManageService {
    /**
     * 메인 페이지 캐시를 갱신합니다.
     */
    void refreshMainPageCache();
}
