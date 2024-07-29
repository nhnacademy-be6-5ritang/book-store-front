package com.nhnacademy.bookstorefront.review.dto.response;

import lombok.Builder;

/**
 * 리뷰 작성 가능 도서 응답 DTO
 *
 * @author 이경헌
 * @version 1.0
 */
@Builder
public record GetBookOrderWithoutReviewResponse(
	Long orderListId,
	String bookTitle) {
}
