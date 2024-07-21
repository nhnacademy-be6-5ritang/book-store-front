package com.nhnacademy.bookstorefront.bookcart.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BookCartRequestTest {

	@Test
	void testCreateBookCartRequest() {
		Long expectedBookId = 1L;
		Integer expectedBookQuantity = 5;

		CreateBookCartRequest request = new CreateBookCartRequest(expectedBookId, expectedBookQuantity);

		assertEquals(expectedBookId, request.bookId());
		assertEquals(expectedBookQuantity, request.bookQuantity());
	}

	@Test
	void testUpdateBookCartRequest() {
		Integer expectedBookQuantity = 5;

		UpdateBookCartRequest request = new UpdateBookCartRequest(expectedBookQuantity);

		assertEquals(expectedBookQuantity, request.bookQuantity());
	}
}
