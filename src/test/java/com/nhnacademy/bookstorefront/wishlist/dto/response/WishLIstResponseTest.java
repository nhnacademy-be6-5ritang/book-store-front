package com.nhnacademy.bookstorefront.wishlist.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class WishListResponseTest {

	@Test
	void testGetWishListResponse() {
		Long expectedWishListId = 1L;
		Long expectedBookId = 2L;
		String expectedBookImageUrl = "http://example.com/image.jpg";
		String expectedBookTitle = "Book Title";
		String expectedAuthorName = "Author Name";
		String expectedPublisherName = "Publisher Name";
		BigDecimal expectedBookSalePrice = new BigDecimal("19.99");
		BigDecimal expectedBookSalePercent = new BigDecimal("10.00");

		GetWishListResponse response = new GetWishListResponse(
			expectedWishListId,
			expectedBookId,
			expectedBookImageUrl,
			expectedBookTitle,
			expectedAuthorName,
			expectedPublisherName,
			expectedBookSalePrice,
			expectedBookSalePercent
		);

		assertEquals(expectedWishListId, response.wishListId());
		assertEquals(expectedBookId, response.bookId());
		assertEquals(expectedBookImageUrl, response.bookImageUrl());
		assertEquals(expectedBookTitle, response.bookTitle());
		assertEquals(expectedAuthorName, response.authorName());
		assertEquals(expectedPublisherName, response.publisherName());
		assertEquals(expectedBookSalePrice, response.bookSalePrice());
		assertEquals(expectedBookSalePercent, response.bookSalePercent());
	}
}
