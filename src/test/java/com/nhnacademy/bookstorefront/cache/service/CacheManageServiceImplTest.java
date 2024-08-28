package com.nhnacademy.bookstorefront.cache.service;

import com.nhnacademy.bookstorefront.cache.service.impl.CacheManageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.Mockito.*;

class CacheManageServiceImplTest {

    @Mock
    private CacheService cacheService;

    @InjectMocks
    private CacheManageServiceImpl cacheManageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRefreshMainPageCache() {
        // Arrange
        doNothing().when(cacheService).initMainPageCache();
        when(cacheService.getBestSellerBooks()).thenReturn(Collections.emptyList());
        when(cacheService.getLikesBooks()).thenReturn(Collections.emptyList());
        when(cacheService.getNewestBooks()).thenReturn(Collections.emptyList());

        // Act
        cacheManageService.refreshMainPageCache();

        // Assert
        verify(cacheService, times(1)).initMainPageCache();
        verify(cacheService, times(1)).getBestSellerBooks();
        verify(cacheService, times(1)).getLikesBooks();
        verify(cacheService, times(1)).getNewestBooks();
    }
}