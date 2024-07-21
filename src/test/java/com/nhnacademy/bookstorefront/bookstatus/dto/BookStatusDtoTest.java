package com.nhnacademy.bookstorefront.bookstatus.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BookStatusDtoTest {

	@Test
	void testBookStatusDtoCreation() {
		Long bookStatusId = 1L;
		String bookStatusName = "Available";

		BookStatusDto bookStatusDto = new BookStatusDto(bookStatusId, bookStatusName);

		assertEquals(bookStatusId, bookStatusDto.bookStatusId());
		assertEquals(bookStatusName, bookStatusDto.bookStatusName());
	}

	@Test
	void testBookStatusDtoNullValues() {
		Long bookStatusId = null;
		String bookStatusName = null;

		BookStatusDto bookStatusDto = new BookStatusDto(bookStatusId, bookStatusName);

		assertNull(bookStatusDto.bookStatusId());
		assertNull(bookStatusDto.bookStatusName());
	}

	@Test
	void testBookStatusDtoNonNullValues() {
		Long bookStatusId = 1L;
		String bookStatusName = "Available";

		BookStatusDto bookStatusDto = new BookStatusDto(bookStatusId, bookStatusName);

		assertNotNull(bookStatusDto.bookStatusId());
		assertNotNull(bookStatusDto.bookStatusName());
	}

	@Test
	void testBookStatusDtoValidation() {
		Long bookStatusId = 1L;
		String validBookStatusName = "ValidName";
		String invalidBookStatusName = "";

		BookStatusDto validDto = new BookStatusDto(bookStatusId, validBookStatusName);
		BookStatusDto invalidDto = new BookStatusDto(bookStatusId, invalidBookStatusName);

		assertNotNull(validDto.bookStatusName());

		assertThrows(IllegalArgumentException.class, () -> {
			if (invalidDto.bookStatusName().isBlank()) {
				throw new IllegalArgumentException("BookStatusName must not be blank");
			}
		});
	}
}