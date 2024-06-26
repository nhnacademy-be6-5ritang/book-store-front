package com.nhnacademy.bookstorefront.review.dto.response;

import java.time.LocalDateTime;

public record GetReviewResponse(Long bookId, Long userId, int reviewScore, String reviewComment,
								LocalDateTime reviewCreatedAt) {
}
