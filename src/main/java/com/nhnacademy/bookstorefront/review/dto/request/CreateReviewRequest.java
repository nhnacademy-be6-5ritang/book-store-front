package com.nhnacademy.bookstorefront.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateReviewRequest(
	@NotNull Long orderListId,
	@NotNull int reviewScore,
	@NotBlank @Size(max = 400) String reviewComment,
	String fileName) {

	public static CreateReviewRequest from(CreateReviewRequest request, String fileName) {
		return CreateReviewRequest.builder()
			.orderListId(request.orderListId)
			.reviewScore(request.reviewScore)
			.reviewComment(request.reviewComment)
			.fileName(fileName)
			.build();
	}
}
