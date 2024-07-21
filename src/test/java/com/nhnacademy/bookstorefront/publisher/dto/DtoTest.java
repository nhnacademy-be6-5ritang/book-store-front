package com.nhnacademy.bookstorefront.publisher.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.nhnacademy.bookstorefront.publisher.dto.response.PublisherDto;

class DtoTest {

	@Test
	void testPublisherDtoCreation() {
		Long publisherId = 1L;
		String publisherName = "Publisher Name";

		PublisherDto dto = new PublisherDto(publisherId, publisherName);

		assertEquals(publisherId, dto.publisherId());
		assertEquals(publisherName, dto.publisherName());
	}

	@Test
	void testEqualsAndHashCode() {
		PublisherDto dto1 = new PublisherDto(1L, "Publisher Name");
		PublisherDto dto2 = new PublisherDto(1L, "Publisher Name");
		PublisherDto dto3 = new PublisherDto(2L, "Another Publisher");

		// Test equality
		assertEquals(dto1, dto2);
		assertNotEquals(dto1, dto3);
		assertNotEquals(dto2, dto3);

		// Test hash code
		assertEquals(dto1.hashCode(), dto2.hashCode());
		assertNotEquals(dto1.hashCode(), dto3.hashCode());
	}

	@Test
	void testToString() {
		PublisherDto dto = new PublisherDto(1L, "Publisher Name");

		String expectedString = "PublisherDto[publisherId=1, publisherName=Publisher Name]";
		assertEquals(expectedString, dto.toString());
	}
}
