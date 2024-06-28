package com.nhnacademy.bookstorefront.review.dto.request;

public record CreateReviewRequest(Long bookId, Long userId, int reviewScore, String reviewComment) {
}
