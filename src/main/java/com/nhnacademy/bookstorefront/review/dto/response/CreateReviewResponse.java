package com.nhnacademy.bookstorefront.review.dto.response;

import java.time.LocalDateTime;

public record CreateReviewResponse(Long reviewId, Long bookId, Long userId, int reviewScore, String reviewComment,
								   LocalDateTime reviewCreatedAt) {
}
