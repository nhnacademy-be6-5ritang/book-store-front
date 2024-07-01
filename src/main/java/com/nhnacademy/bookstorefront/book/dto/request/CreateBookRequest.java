package com.nhnacademy.bookstorefront.book.dto.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.tag.dto.response.TagDto;

/**
 * 책 상세페이지 응답 DTO
 *
 * @author 김기욱
 * @version 1.0
 */
public record CreateBookRequest(
	String bookIsbn,
	List<GetCategoryResponse> categories,
	List<TagDto> tags,
	String bookTitle,
	String authorName,
	String publisherName,
	@DateTimeFormat(pattern = "yyyy-MM-dd") Date bookPublishDate,
	String bookStatusName,
	String bookIndex,
	String bookDescription,
	int bookQuantity,
	boolean bookPackaging,
	BigDecimal bookPrice,
	BigDecimal bookSalePrice,
	BigDecimal bookSalePercent) {
}