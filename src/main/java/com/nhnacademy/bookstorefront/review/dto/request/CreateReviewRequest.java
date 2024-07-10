package com.nhnacademy.bookstorefront.review.dto.request;

public record CreateReviewRequest(
	Long bookId,
	int reviewScore,
	String reviewComment) {
}
