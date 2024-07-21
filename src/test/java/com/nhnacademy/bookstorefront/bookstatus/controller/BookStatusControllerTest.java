package com.nhnacademy.bookstorefront.bookstatus.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.bookstatus.dto.response.BookStatusDto;
import com.nhnacademy.bookstorefront.bookstatus.service.impl.BookStatusServiceImpl;

class BookStatusControllerTest {

	private MockMvc mockMvc;

	@Mock
	private BookStatusServiceImpl bookStatusService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new BookStatusController(bookStatusService))
			.build();
	}

	@Test
	void testCreateBookStatusForm() throws Exception {
		mockMvc.perform(get("/api/bookStatuses/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("bookStatus/create-bookStatus"));
	}

	@Test
	void testUpdateBookStatusForm() throws Exception {
		Long bookStatusId = 1L;
		BookStatusDto bookStatusDto = new BookStatusDto(bookStatusId, "Available");
		when(bookStatusService.getBookStatus(bookStatusId)).thenReturn(bookStatusDto);

		mockMvc.perform(get("/api/bookStatuses/update/{bookStatusId}", bookStatusId))
			.andExpect(status().isOk())
			.andExpect(view().name("bookStatus/update-bookStatus"))
			.andExpect(model().attribute("bookStatus", bookStatusDto));
	}

	@Test
	void testGetBookStatuses() throws Exception {
		BookStatusDto bookStatusDto = new BookStatusDto(1L, "Available");
		List<BookStatusDto> bookStatusList = List.of(bookStatusDto);

		when(bookStatusService.getBookStatuses()).thenReturn(bookStatusList);

		mockMvc.perform(get("/api/bookStatuses"))
			.andExpect(status().isOk())
			.andExpect(view().name("bookStatus/list-bookStatus"))
			.andExpect(model().attribute("bookStatuses", bookStatusList));
	}

	@Test
	void testCreateBookStatus() throws Exception {
		BookStatusDto bookStatusDto = new BookStatusDto(null, "Available");

		mockMvc.perform(post("/api/bookStatuses")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("bookStatusName", bookStatusDto.bookStatusName()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/bookStatuses"));

		verify(bookStatusService).createBookStatus(any(BookStatusDto.class));
	}

	@Test
	void testUpdateBookStatus() throws Exception {
		Long bookStatusId = 1L;
		BookStatusDto bookStatusDto = new BookStatusDto(bookStatusId, "Status");
		
		doNothing().when(bookStatusService).updateBookStatus(eq(bookStatusId), any(BookStatusDto.class));

		mockMvc.perform(put("/api/bookStatuses/{bookStatusId}", bookStatusId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("bookStatusId", bookStatusDto.bookStatusId().toString())
				.param("bookStatusName", bookStatusDto.bookStatusName()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/bookStatuses"));

		verify(bookStatusService).updateBookStatus(eq(bookStatusId), any(BookStatusDto.class));
	}

	@Test
	void testDeleteBookStatus() throws Exception {
		Long bookStatusId = 1L;

		mockMvc.perform(delete("/api/bookStatuses/{bookStatusId}", bookStatusId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/bookStatuses"));

		verify(bookStatusService).deleteBookStatus(bookStatusId);
	}
}