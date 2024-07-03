package com.nhnacademy.bookstorefront.category.dto.response;

public record CreateCategoryResponse(
	String categoryName,
	Long parentCategoryId) {
}
