package com.nhnacademy.bookstorefront.category.dto.request;

public record CreateCategoryRequest(
	String categoryName,
	Long parentCategoryId) {
}
