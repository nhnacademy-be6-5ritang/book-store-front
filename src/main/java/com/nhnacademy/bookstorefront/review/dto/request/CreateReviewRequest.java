package com.nhnacademy.bookstorefront.review.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record CreateReviewRequest(
	Long bookId,
	int reviewScore,
	String reviewComment) {
}
