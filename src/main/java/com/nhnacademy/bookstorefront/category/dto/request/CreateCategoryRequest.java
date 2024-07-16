package com.nhnacademy.bookstorefront.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
	@NotBlank @Size(max = 20) String categoryName,
	Long parentCategoryId) {
}
