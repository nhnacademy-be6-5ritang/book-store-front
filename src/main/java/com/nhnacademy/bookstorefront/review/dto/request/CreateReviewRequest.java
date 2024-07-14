package com.nhnacademy.bookstorefront.review.dto.request;

import lombok.Builder;

@Builder
public record CreateReviewRequest(
	Long bookId,
	int reviewScore,
	String reviewComment,
	String fileName) {

	public static CreateReviewRequest from(CreateReviewRequest request, String fileName) {
		return CreateReviewRequest.builder()
			.bookId(request.bookId)
			.reviewScore(request.reviewScore)
			.reviewComment(request.reviewComment)
			.fileName(fileName)
			.build();
	}
}
