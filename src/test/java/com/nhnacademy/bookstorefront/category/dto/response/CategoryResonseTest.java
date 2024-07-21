package com.nhnacademy.bookstorefront.category.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CategoryResponseTest {

	@Test
	void testCategorySearchResult() {
		Long categoryId = 1L;
		String categoryName = "Fiction";

		CategorySearchResult response = new CategorySearchResult(categoryId, categoryName);

		assertNotNull(response);
		assertEquals(categoryId, response.categoryId());
		assertEquals(categoryName, response.categoryName());
	}

	@Test
	void testGetCategoryResponse() {
		Long categoryId = 1L;
		String categoryName = "Fiction";
		String parentCategoryName = "Books";

		GetCategoryResponse response = new GetCategoryResponse(categoryId, categoryName, parentCategoryName);

		assertNotNull(response);
		assertEquals(categoryId, response.categoryId());
		assertEquals(categoryName, response.categoryName());
		assertEquals(parentCategoryName, response.parentCategoryName());
	}
}
