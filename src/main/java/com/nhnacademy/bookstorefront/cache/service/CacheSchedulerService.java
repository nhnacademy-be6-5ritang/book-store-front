package com.nhnacademy.bookstorefront.cache.service;

/**
 * @author 이경헌
 * 캐시 자원을 스케줄링하기 위한 서비스를 위한 인터페이스.
 */
public interface CacheSchedulerService {
    /**
     * 메인 페이지 캐시를 갱신합니다.
     * 이 메서드는 일반적으로 정기적으로 실행되도록 스케줄링되어 캐시 데이터를 최신 상태로 유지합니다.
     */
    void refreshMainPageCache();
}
