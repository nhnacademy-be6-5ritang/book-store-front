package com.nhnacademy.bookstorefront.bookcart.dto.response;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record GetBookCartResponse(
	Long bookId,
	String cartId,
	String bookImageUrl,
	String bookTitle,
	String authorName,
	String publisherName,
	BigDecimal bookPrice,
	BigDecimal bookSalePrice,
	BigDecimal bookSalePercent,
	int inventorQuantity,
	int bookQuantity
) {
}
