package com.nhnacademy.bookstorefront.publisher.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.global.controller.GlobalDataControllerAdvice;
import com.nhnacademy.bookstorefront.publisher.dto.response.PublisherDto;
import com.nhnacademy.bookstorefront.publisher.service.impl.PublisherServiceImpl;

class PublisherControllerTest {

	private MockMvc mockMvc;

	@Mock
	private PublisherServiceImpl publisherService;

	@MockBean
	private GlobalDataControllerAdvice globalDataControllerAdvice;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new PublisherController(publisherService))
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	void testCreatePublisherForm() throws Exception {
		mockMvc.perform(get("/api/publishers/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("publisher/create-publisher"));
	}

	@Test
	void testUpdatePublisherForm() throws Exception {
		Long publisherId = 1L;
		PublisherDto publisherDto = new PublisherDto(publisherId, "Publisher Name");

		when(publisherService.getPublisher(anyLong())).thenReturn(publisherDto);

		mockMvc.perform(get("/api/publishers/update/{publisherId}", publisherId))
			.andExpect(status().isOk())
			.andExpect(view().name("publisher/update-publisher"));
	}

	@Test
	void testGetPublishers() throws Exception {
		PublisherDto publisherDto = new PublisherDto(1L, "Publisher Name");
		List<PublisherDto> publishersList = List.of(publisherDto);
		Page<PublisherDto> publisherPage = new PageImpl<>(publishersList);

		when(publisherService.getPublishers(any(Pageable.class))).thenReturn(publisherPage);

		mockMvc.perform(get("/api/publishers/page"))
			.andExpect(status().isOk())
			.andExpect(view().name("publisher/list-publisher"));
	}

	@Test
	void testGetPublishersPage() throws Exception {
		PublisherDto publisherDto = new PublisherDto(1L, "Publisher Name");
		when(publisherService.getPublishers()).thenReturn(Collections.singletonList(publisherDto));

		mockMvc.perform(get("/api/publishers"))
			.andExpect(status().isOk())
			.andExpect(view().name("publisher/list-publisher"))
			.andExpect(model().attributeExists("publishers"))
			.andExpect(model().attribute("publishers", Collections.singletonList(publisherDto)));
	}

	@Test
	void testCreatePublisher() throws Exception {
		PublisherDto publisherDto = new PublisherDto(null, "New Publisher");

		mockMvc.perform(post("/api/publishers")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("publisherName", publisherDto.publisherName()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/publishers/page"));

		verify(publisherService).createPublisher(any(PublisherDto.class));
	}

	@Test
	void testUpdatePublisher() throws Exception {
		Long publisherId = 1L;
		PublisherDto publisherDto = new PublisherDto(publisherId, "Updated Publisher");

		mockMvc.perform(put("/api/publishers/{publisherId}", publisherId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("publisherName", publisherDto.publisherName()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/publishers/page"));

		verify(publisherService).updatePublisher(anyLong(), any(PublisherDto.class));
	}

	@Test
	void testDeletePublisher() throws Exception {
		Long publisherId = 1L;

		mockMvc.perform(delete("/api/publishers/{publisherId}", publisherId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/publishers/page"));

		verify(publisherService).deletePublisher(publisherId);
	}
}