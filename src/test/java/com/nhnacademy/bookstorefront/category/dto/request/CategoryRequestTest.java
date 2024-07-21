package com.nhnacademy.bookstorefront.category.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CategoryRequestTest {

	@Test
	void testCreateCategoryRequest() {
		String categoryName = "Fiction";
		Long parentCategoryId = 1L;

		CreateCategoryRequest request = new CreateCategoryRequest(categoryName, parentCategoryId);

		assertNotNull(request);
		assertEquals(categoryName, request.categoryName());
		assertEquals(parentCategoryId, request.parentCategoryId());
	}

	@Test
	void testUpdateCategoryRequest() {
		String categoryName = "Non-Fiction";
		Long parentCategoryId = 2L;

		UpdateCategoryRequest request = new UpdateCategoryRequest(categoryName, parentCategoryId);

		assertNotNull(request);
		assertEquals(categoryName, request.categoryName());
		assertEquals(parentCategoryId, request.parentCategoryId());
	}
}
