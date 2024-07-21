package com.nhnacademy.bookstorefront.bookstatus.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.bookstatus.dto.response.BookStatusDto;
import com.nhnacademy.bookstorefront.bookstatus.feignclient.BookStatusServiceClient;
import com.nhnacademy.bookstorefront.bookstatus.service.impl.BookStatusServiceImpl;

class BookStatusServiceImplTest {

	@Mock
	private BookStatusServiceClient bookStatusServiceClient;

	@InjectMocks
	private BookStatusServiceImpl bookStatusService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetBookStatuses() {
		BookStatusDto bookStatusDto = new BookStatusDto(1L, "Available");
		List<BookStatusDto> bookStatusList = List.of(bookStatusDto);
		ResponseEntity<List<BookStatusDto>> responseEntity = ResponseEntity.ok(bookStatusList);

		when(bookStatusServiceClient.getBookStatuses()).thenReturn(responseEntity);

		List<BookStatusDto> result = bookStatusService.getBookStatuses();

		verify(bookStatusServiceClient).getBookStatuses();
		assertEquals(bookStatusList, result);
	}

	@Test
	void testGetBookStatus() {
		Long bookStatusId = 1L;
		BookStatusDto bookStatusDto = new BookStatusDto(bookStatusId, "Available");
		ResponseEntity<BookStatusDto> responseEntity = ResponseEntity.ok(bookStatusDto);

		when(bookStatusServiceClient.getBookStatus(bookStatusId)).thenReturn(responseEntity);

		BookStatusDto result = bookStatusService.getBookStatus(bookStatusId);

		verify(bookStatusServiceClient).getBookStatus(bookStatusId);
		assertEquals(bookStatusDto, result);
	}

	@Test
	void testCreateBookStatus() {
		BookStatusDto bookStatusDto = new BookStatusDto(1L, "Available");

		bookStatusService.createBookStatus(bookStatusDto);

		verify(bookStatusServiceClient).createBookStatus(bookStatusDto);
	}

	@Test
	void testUpdateBookStatus() {
		Long bookStatusId = 1L;
		BookStatusDto bookStatusDto = new BookStatusDto(bookStatusId, "Updated Status");

		bookStatusService.updateBookStatus(bookStatusId, bookStatusDto);

		verify(bookStatusServiceClient).updateBookStatus(eq(bookStatusId), any(BookStatusDto.class));
	}

	@Test
	void testDeleteBookStatus() {
		Long bookStatusId = 1L;

		bookStatusService.deleteBookStatus(bookStatusId);

		verify(bookStatusServiceClient).deleteBookStatus(bookStatusId);
	}
}
