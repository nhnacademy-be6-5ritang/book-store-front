package com.nhnacademy.bookstorefront.search.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 책 상세페이지 응답 DTO
 */
@Data
@Builder
public class BookSearchResponse {
	private Long bookId;
	private String authorName;
	private String publisherName;
	private String bookStatusName;
	private String bookTitle;
	private String bookDescription;
	private int bookQuantity;
	private Date bookPublishDate;
	private String bookIsbn;
	private BigDecimal bookPrice;
	private BigDecimal bookSalePrice;
	private BigDecimal bookSalePercent;
	private String bookImageUrl;
}
