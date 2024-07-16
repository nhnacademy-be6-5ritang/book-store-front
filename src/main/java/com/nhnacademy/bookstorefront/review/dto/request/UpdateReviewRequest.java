package com.nhnacademy.bookstorefront.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateReviewRequest(
	@NotNull int reviewScore,
	@NotBlank @Size(max = 400) String reviewComment) {
}
