package com.nhnacademy.bookstorefront.category.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.category.dto.request.CreateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.request.UpdateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.response.CategorySearchResult;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.category.service.impl.CategoryServiceImpl;

class CategoryControllerTest {

	private MockMvc mockMvc;

	@Mock
	private CategoryServiceImpl categoryService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new CategoryController(categoryService))
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
	}

	@Test
	void testCreateCategoryForm() throws Exception {
		mockMvc.perform(get("/api/categories/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("category/create-category"));
	}

	@Test
	void testUpdateCategoryForm() throws Exception {
		Long categoryId = 1L;
		GetCategoryResponse getResponse = new GetCategoryResponse(
			categoryId,
			"Fiction",
			"Books"
		);
		when(categoryService.getCategory(categoryId)).thenReturn(getResponse);
		when(categoryService.getCategories()).thenReturn(List.of(getResponse));

		mockMvc.perform(get("/api/categories/update/{categoryId}", categoryId))
			.andExpect(status().isOk())
			.andExpect(view().name("category/update-category"))
			.andExpect(model().attribute("category", getResponse))
			.andExpect(model().attribute("categories", List.of(getResponse)));
	}

	@Test
	void testGetCategories() throws Exception {
		GetCategoryResponse response = new GetCategoryResponse(
			1L,
			"Fiction",
			"Books"
		);
		List<GetCategoryResponse> categoriesList = List.of(response);

		when(categoryService.getCategories()).thenReturn(categoriesList);

		mockMvc.perform(get("/api/categories"))
			.andExpect(status().isOk())
			.andExpect(view().name("category/list-category"))
			.andExpect(model().attribute("categories", categoriesList));
	}

	@Test
	void testGetCategoriesWithPageable() throws Exception {
		GetCategoryResponse response = new GetCategoryResponse(
			1L,
			"Fiction",
			"Books"
		);
		Page<GetCategoryResponse> categoriesPage = new PageImpl<>(List.of(response), PageRequest.of(0, 10), 1);
		when(categoryService.getCategories(any(Pageable.class))).thenReturn(categoriesPage);

		mockMvc.perform(get("/api/categories/page")
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk())
			.andExpect(view().name("category/list-category"))
			.andExpect(model().attribute("categories", categoriesPage));
	}

	@Test
	void testCreateCategory() throws Exception {
		CreateCategoryRequest createRequest = new CreateCategoryRequest(
			"Science",
			null
		);

		mockMvc.perform(post("/api/categories")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("categoryName", createRequest.categoryName())
				.param("parentCategoryId",
					createRequest.parentCategoryId() != null ? createRequest.parentCategoryId().toString() : ""))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/categories/page"));

		verify(categoryService).createCategory(any(CreateCategoryRequest.class));
	}

	@Test
	void testUpdateCategory() throws Exception {
		Long categoryId = 1L;
		UpdateCategoryRequest updateRequest = new UpdateCategoryRequest(
			"Updated Fiction",
			null
		);

		mockMvc.perform(put("/api/categories/{categoryId}", categoryId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("categoryName", updateRequest.categoryName())
				.param("parentCategoryId",
					updateRequest.parentCategoryId() != null ? updateRequest.parentCategoryId().toString() : ""))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/categories/page"));

		verify(categoryService).updateCategory(eq(categoryId), any(UpdateCategoryRequest.class));
	}

	@Test
	void testDeleteCategory() throws Exception {
		Long categoryId = 1L;

		mockMvc.perform(delete("/api/categories/{categoryId}", categoryId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/categories/page"));

		verify(categoryService).deleteCategory(categoryId);
	}

	@Test
	void testSearchCategories() throws Exception {
		CategorySearchResult result = new CategorySearchResult(
			1L,
			"Science"
		);
		List<CategorySearchResult> searchResults = List.of(result);
		when(categoryService.searchCategories(anyString())).thenReturn(searchResults);

		mockMvc.perform(get("/api/categories/search/test")
				.param("key", "Science"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].categoryId").value(result.categoryId()))
			.andExpect(jsonPath("$[0].categoryName").value(result.categoryName()));
	}
}
