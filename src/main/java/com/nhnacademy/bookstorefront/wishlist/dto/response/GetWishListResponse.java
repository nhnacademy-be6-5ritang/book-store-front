package com.nhnacademy.bookstorefront.wishlist.dto.response;

import java.math.BigDecimal;

public record GetWishListResponse(
	Long wishListId,
	Long bookId,
	String bookImageUrl,
	String bookTitle,
	String authorName,
	String publisherName,
	BigDecimal bookSalePrice,
	BigDecimal bookSalePercent) {
}
