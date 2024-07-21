package com.nhnacademy.bookstorefront.category.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.category.dto.request.CreateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.request.UpdateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.response.CategorySearchResult;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.category.feignclient.CategoryServiceClient;

class CategoryServiceImplTest {

	@Mock
	private CategoryServiceClient categoryServiceClient;

	@InjectMocks
	private CategoryServiceImpl categoryService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetCategories() {
		GetCategoryResponse response = new GetCategoryResponse(1L, "Fiction", "Books");
		List<GetCategoryResponse> responses = List.of(response);
		when(categoryServiceClient.getCategories()).thenReturn(ResponseEntity.ok(responses));

		List<GetCategoryResponse> result = categoryService.getCategories();

		assertEquals(responses, result);
		verify(categoryServiceClient).getCategories();
	}

	@Test
	void testGetCategoriesWithPageable() {
		GetCategoryResponse response = new GetCategoryResponse(1L, "Fiction", "Books");
		Pageable pageable = PageRequest.of(0, 10);
		Page<GetCategoryResponse> responsePage = new PageImpl<>(List.of(response), pageable, 1);
		when(categoryServiceClient.getCategories(any(Pageable.class))).thenReturn(ResponseEntity.ok(responsePage));

		Page<GetCategoryResponse> result = categoryService.getCategories(pageable);

		assertEquals(responsePage, result);
		verify(categoryServiceClient).getCategories(any(Pageable.class));
	}

	@Test
	void testGetCategoriesByBookId() {
		GetCategoryResponse response = new GetCategoryResponse(1L, "Fiction", "Books");
		List<GetCategoryResponse> responses = List.of(response);
		Long bookId = 1L;
		when(categoryServiceClient.getCategoriesByBookId(bookId)).thenReturn(ResponseEntity.ok(responses));

		List<GetCategoryResponse> result = categoryService.getCategoriesByBookId(bookId);

		assertEquals(responses, result);
		verify(categoryServiceClient).getCategoriesByBookId(bookId);
	}

	@Test
	void testGetCategory() {
		GetCategoryResponse response = new GetCategoryResponse(1L, "Fiction", "Books");
		Long categoryId = 1L;
		when(categoryServiceClient.getCategory(categoryId)).thenReturn(ResponseEntity.ok(response));

		GetCategoryResponse result = categoryService.getCategory(categoryId);

		assertEquals(response, result);
		verify(categoryServiceClient).getCategory(categoryId);
	}

	@Test
	void testCreateCategory() {
		CreateCategoryRequest request = new CreateCategoryRequest("Fiction", 1L);

		categoryService.createCategory(request);

		verify(categoryServiceClient).createCategory(request);
	}

	@Test
	void testUpdateCategory() {
		UpdateCategoryRequest request = new UpdateCategoryRequest("Fiction Updated", 1L);
		Long categoryId = 1L;

		categoryService.updateCategory(categoryId, request);

		verify(categoryServiceClient).updateCategory(eq(categoryId), eq(request));
	}

	@Test
	void testDeleteCategory() {
		Long categoryId = 1L;

		categoryService.deleteCategory(categoryId);

		verify(categoryServiceClient).deleteCategory(categoryId);
	}

	@Test
	void testSearchCategories() {
		CategorySearchResult result = new CategorySearchResult(1L, "Fiction");
		List<CategorySearchResult> results = List.of(result);
		String query = "Fiction";
		when(categoryServiceClient.searchCategories(query)).thenReturn(ResponseEntity.ok(results));

		List<CategorySearchResult> searchResults = categoryService.searchCategories(query);

		assertEquals(results, searchResults);
		verify(categoryServiceClient).searchCategories(query);
	}
}