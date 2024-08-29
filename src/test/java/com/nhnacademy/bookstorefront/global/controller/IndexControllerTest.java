package com.nhnacademy.bookstorefront.global.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.cache.service.impl.CacheServiceImpl;

@WebMvcTest(IndexController.class)
class IndexControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CacheServiceImpl cacheService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new IndexController()).build();
	}

	@Test
	void testIndexPageRedirect() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/main"));
	}

	@Test
	void testAdminPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/admin"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("admin/admin-account"));
	}

	@Test
	void testUserPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/me"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("user/user-account"));
	}

	@Test
	void testShowTemplatePage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/template"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("menu-template"));
	}
}