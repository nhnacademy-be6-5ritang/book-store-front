package com.nhnacademy.bookstorefront.category.dto.request;

public record UpdateCategoryRequest(
	String categoryName,
	Long parentCategoryId) {
}
