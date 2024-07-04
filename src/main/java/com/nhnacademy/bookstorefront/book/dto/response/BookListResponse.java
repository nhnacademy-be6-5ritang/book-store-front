package com.nhnacademy.bookstorefront.book.dto.response;

import java.math.BigDecimal;

/**
 * 책 리스트 응답 DTO
 *
 * @author 김기욱
 * @version 1.0
 */
public record BookListResponse(
	Long bookId,
	String bookTitle,
	String bookIsbn,
	BigDecimal bookPrice,
	BigDecimal bookSalePrice,
	BigDecimal bookSalePercent) {
}

// TODO : 이미지 추가
