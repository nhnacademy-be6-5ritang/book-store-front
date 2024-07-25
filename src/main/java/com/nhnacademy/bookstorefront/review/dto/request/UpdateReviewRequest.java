package com.nhnacademy.bookstorefront.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UpdateReviewRequest(
	@NotNull int reviewScore,
	@NotBlank @Size(max = 400) String reviewComment,
	String fileName) {

	public static UpdateReviewRequest from(UpdateReviewRequest request, String fileName) {
		return UpdateReviewRequest.builder()
			.reviewScore(request.reviewScore)
			.reviewComment(request.reviewComment)
			.fileName(fileName)
			.build();
	}
}
