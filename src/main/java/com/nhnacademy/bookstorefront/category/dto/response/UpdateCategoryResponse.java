package com.nhnacademy.bookstorefront.category.dto.response;

public record UpdateCategoryResponse(
	String categoryName,
	String parentCategoryName) {
}
