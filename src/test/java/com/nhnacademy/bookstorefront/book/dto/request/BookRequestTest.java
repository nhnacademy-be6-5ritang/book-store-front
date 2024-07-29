package com.nhnacademy.bookstorefront.book.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class BookRequestTest {

	@Test
	void testCreateBookRequest() {
		String isbn = "1234567890";
		List<Long> categories = List.of(1L, 2L);
		List<Long> tags = List.of(3L, 4L);
		String title = "Book Title";
		String author = "Author Name";
		String publisher = "Publisher Name";
		Date publishDate = new Date();
		String status = "Available";
		String description = "Book Description";
		int quantity = 100;
		BigDecimal price = BigDecimal.valueOf(29.99);
		BigDecimal salePrice = BigDecimal.valueOf(19.99);
		BigDecimal salePercent = BigDecimal.valueOf(0.33);
		String fileName = "test.png";

		CreateBookRequest request = new CreateBookRequest(
			isbn, categories, tags, title, author, publisher, publishDate, status, description, quantity, price,
			salePrice, salePercent, fileName);

		assertEquals(isbn, request.bookIsbn());
		assertEquals(categories, request.categories());
		assertEquals(tags, request.tags());
		assertEquals(title, request.bookTitle());
		assertEquals(author, request.authorName());
		assertEquals(publisher, request.publisherName());
		assertEquals(publishDate, request.bookPublishDate());
		assertEquals(status, request.bookStatusName());
		assertEquals(description, request.bookDescription());
		assertEquals(quantity, request.bookQuantity());
		assertEquals(price, request.bookPrice());
		assertEquals(salePrice, request.bookSalePrice());
		assertEquals(salePercent, request.bookSalePercent());
	}

	@Test
	void testUpdateBookRequest() {
		String isbn = "0987654321";
		List<Long> categories = List.of(5L, 6L);
		List<Long> tags = List.of(7L, 8L);
		String title = "Updated Title";
		String author = "Updated Author";
		String publisher = "Updated Publisher";
		Date publishDate = new Date();
		String status = "Out of Stock";
		String description = "Updated Description";
		int quantity = 50;
		BigDecimal price = BigDecimal.valueOf(39.99);
		BigDecimal salePrice = BigDecimal.valueOf(29.99);
		BigDecimal salePercent = BigDecimal.valueOf(0.25);
		String fileName = "test.png";

		UpdateBookRequest request = new UpdateBookRequest(
			isbn, categories, tags, title, author, publisher, publishDate, status, description, quantity, price,
			salePrice, salePercent, fileName);

		assertEquals(isbn, request.bookIsbn());
		assertEquals(categories, request.categories());
		assertEquals(tags, request.tags());
		assertEquals(title, request.bookTitle());
		assertEquals(author, request.authorName());
		assertEquals(publisher, request.publisherName());
		assertEquals(publishDate, request.bookPublishDate());
		assertEquals(status, request.bookStatusName());
		assertEquals(description, request.bookDescription());
		assertEquals(quantity, request.bookQuantity());
		assertEquals(price, request.bookPrice());
		assertEquals(salePrice, request.bookSalePrice());
		assertEquals(salePercent, request.bookSalePercent());
	}
}