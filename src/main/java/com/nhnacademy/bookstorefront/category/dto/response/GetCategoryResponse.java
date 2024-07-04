package com.nhnacademy.bookstorefront.category.dto.response;

public record GetCategoryResponse(
	Long categoryId,
	String categoryName,
	String parentCategoryName) {
}
