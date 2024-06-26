package com.nhnacademy.bookstorefront.wishlist.dto.response;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record GetWishListResponse(
	Long wishListId,
	String bookTitle,
	String authorName,
	String publisherName,
	BigDecimal bookSalePrice,
	BigDecimal bookSalePercent,
	int bookQuantity) {
}
