package com.nhnacademy.bookstorefront.cache.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.cache.service.impl.CacheServiceImpl;

@WebMvcTest(CacheController.class)
class CacheControllerTest {

	@MockBean
	private CacheServiceImpl cacheService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new CacheController(cacheService))
			.build();
	}

	@Test
	void testCacheManagePage() throws Exception {
		mockMvc.perform(get("/caches"))
			.andExpect(status().isOk())
			.andExpect(view().name("cache/main-cache"));
	}

	@Test
	void testRefreshCacheMainPage() throws Exception {
		doNothing().when(cacheService).refreshMainPageCache();

		mockMvc.perform(get("/caches/mainPage")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(""));

		verify(cacheService, times(1)).refreshMainPageCache();
	}
}