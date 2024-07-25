package com.nhnacademy.bookstorefront.review.dto.response;

import java.time.LocalDateTime;

public record GetReviewResponse(
	Long reviewId,
	Long bookId,
	String userName,
	int reviewScore,
	String reviewComment,
	LocalDateTime reviewCreatedAt,
	String reviewImageUrl) {
}
