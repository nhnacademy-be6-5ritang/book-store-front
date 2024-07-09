package com.nhnacademy.bookstorefront.bookcart.dto.response;

import java.math.BigDecimal;

public record GetBookCartResponse(
	Long bookCartId,
	Long bookId,
	Long cartId,
	String bookImageUrl,
	String bookTitle,
	String authorName,
	String publisherName,
	BigDecimal bookPrice,
	BigDecimal bookSalePrice,
	BigDecimal bookSalePercent,
	int inventorQuantity,
	int bookQuantity) {
}
