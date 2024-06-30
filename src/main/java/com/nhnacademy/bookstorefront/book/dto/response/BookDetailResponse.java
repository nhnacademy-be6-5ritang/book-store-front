package com.nhnacademy.bookstorefront.book.dto.response;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 책 상세페이지 응답 DTO
 *
 * @author 김기욱
 * @version 1.0
 */
public record BookDetailResponse(
	String authorName,
	String publisherName,
	String bookStatusName,
	String bookTitle,
	String bookDescription,
	String bookIndex,
	boolean bookPackaging,
	int bookQuantity,
	Date bookPublishDate,
	String bookIsbn,
	BigDecimal bookPrice,
	BigDecimal bookSalePrice,
	BigDecimal bookSalePercent) {
}