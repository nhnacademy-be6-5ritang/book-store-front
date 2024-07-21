package com.nhnacademy.bookstorefront.bookcart.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class BookCartResponseTest {

	@Test
	void testGetBookCartResponse() {
		Long expectedBookId = 1L;
		String expectedCartId = "testCartId";
		String expectedBookImageUrl = "http://example.com/image.jpg";
		String expectedBookTitle = "Book Title";
		String expectedAuthorName = "Author Name";
		String expectedPublisherName = "Publisher Name";
		BigDecimal expectedBookPrice = BigDecimal.TEN;
		BigDecimal expectedBookSalePrice = BigDecimal.ONE;
		BigDecimal expectedBookSalePercent = BigDecimal.ZERO;
		int expectedInventorQuantity = 100;
		Integer expectedBookQuantity = 2;

		GetBookCartResponse response = new GetBookCartResponse(
			expectedBookId,
			expectedCartId,
			expectedBookImageUrl,
			expectedBookTitle,
			expectedAuthorName,
			expectedPublisherName,
			expectedBookPrice,
			expectedBookSalePrice,
			expectedBookSalePercent,
			expectedInventorQuantity,
			expectedBookQuantity
		);

		assertEquals(expectedBookId, response.bookId());
		assertEquals(expectedCartId, response.cartId());
		assertEquals(expectedBookImageUrl, response.bookImageUrl());
		assertEquals(expectedBookTitle, response.bookTitle());
		assertEquals(expectedAuthorName, response.authorName());
		assertEquals(expectedPublisherName, response.publisherName());
		assertEquals(expectedBookPrice, response.bookPrice());
		assertEquals(expectedBookSalePrice, response.bookSalePrice());
		assertEquals(expectedBookSalePercent, response.bookSalePercent());
		assertEquals(expectedInventorQuantity, response.inventorQuantity());
		assertEquals(expectedBookQuantity, response.bookQuantity());
	}
}
