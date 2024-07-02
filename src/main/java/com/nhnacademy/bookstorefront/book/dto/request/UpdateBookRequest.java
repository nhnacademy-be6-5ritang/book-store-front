package com.nhnacademy.bookstorefront.book.dto.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 책 상세페이지 응답 DTO
 *
 * @author 김기욱
 * @version 1.0
 */
public record UpdateBookRequest(
	List<Long> categories,
	List<Long> tags,
	String authorName,
	String publisherName,
	String bookStatusName,
	String bookTitle,
	String bookIndex,
	String bookDescription,
	int bookQuantity,
	boolean bookPackaging,
	@DateTimeFormat(pattern = "yyyy-MM-dd") Date bookPublishDate,
	String bookIsbn,
	BigDecimal bookPrice,
	BigDecimal bookSalePrice,
	BigDecimal bookSalePercent) {
}
