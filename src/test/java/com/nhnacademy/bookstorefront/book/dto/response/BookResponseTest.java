package com.nhnacademy.bookstorefront.book.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;

class BookResponseTest {

	@Test
	void testBookSearchResult() {
		Long bookId = 1L;
		String bookTitle = "Sample Book Title";

		BookSearchResult result = new BookSearchResult(bookId, bookTitle);

		assertEquals(bookId, result.bookId());
		assertEquals(bookTitle, result.bookTitle());
	}

	@Test
	void testGetBookDetailResponse() {
		Long bookId = 1L;
		String authorName = "Author Name";
		String publisherName = "Publisher Name";
		String bookStatusName = "Available";
		String bookTitle = "Sample Book Title";
		String bookDescription = "Book Description";
		int bookQuantity = 100;
		Date bookPublishDate = new Date();
		String bookIsbn = "1234567890";
		BigDecimal bookPrice = BigDecimal.valueOf(29.99);
		BigDecimal bookSalePrice = BigDecimal.valueOf(19.99);
		BigDecimal bookSalePercent = BigDecimal.valueOf(0.33);
		String bookImageUrl = "http://example.com/image.jpg";

		GetBookDetailResponse response = new GetBookDetailResponse(
			bookId, authorName, publisherName, bookStatusName, bookTitle, bookDescription,
			bookQuantity, bookPublishDate, bookIsbn, bookPrice, bookSalePrice, bookSalePercent, bookImageUrl
		);

		assertEquals(bookId, response.bookId());
		assertEquals(authorName, response.authorName());
		assertEquals(publisherName, response.publisherName());
		assertEquals(bookStatusName, response.bookStatusName());
		assertEquals(bookTitle, response.bookTitle());
		assertEquals(bookDescription, response.bookDescription());
		assertEquals(bookQuantity, response.bookQuantity());
		assertEquals(bookPublishDate, response.bookPublishDate());
		assertEquals(bookIsbn, response.bookIsbn());
		assertEquals(bookPrice, response.bookPrice());
		assertEquals(bookSalePrice, response.bookSalePrice());
		assertEquals(bookSalePercent, response.bookSalePercent());
		assertEquals(bookImageUrl, response.bookImageUrl());
	}

	@Test
	void testGetBookTitleResponse() {
		Long bookId = 1L;
		String bookTitle = "Sample Book Title";

		GetBookTitleResponse response = new GetBookTitleResponse(bookId, bookTitle);

		assertEquals(bookId, response.bookId());
		assertEquals(bookTitle, response.bookTitle());
	}
}
