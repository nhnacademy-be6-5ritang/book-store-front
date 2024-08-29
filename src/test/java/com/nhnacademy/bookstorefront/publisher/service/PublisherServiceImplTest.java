package com.nhnacademy.bookstorefront.publisher.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.publisher.dto.response.PublisherDto;
import com.nhnacademy.bookstorefront.publisher.feignclient.PublisherServiceClient;
import com.nhnacademy.bookstorefront.publisher.service.impl.PublisherServiceImpl;

class PublisherServiceImplTest {

	@Mock
	private PublisherServiceClient publisherServiceClient;

	@InjectMocks
	private PublisherServiceImpl publisherService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetPublishersWithPageable() {
		PublisherDto publisherDto = new PublisherDto(1L, "Publisher Name");
		Page<PublisherDto> publishersPage = new PageImpl<>(Collections.singletonList(publisherDto));

		when(publisherServiceClient.getPublishers(any(Pageable.class))).thenReturn(ResponseEntity.ok(publishersPage));

		Page<PublisherDto> result = publisherService.getPublishers(Pageable.unpaged());

		verify(publisherServiceClient).getPublishers(any(Pageable.class));
		assertEquals(publishersPage, result);
	}

	@Test
	void testGetPublisher() {
		PublisherDto publisherDto = new PublisherDto(1L, "Publisher Name");

		when(publisherServiceClient.getPublisher(anyLong())).thenReturn(ResponseEntity.ok(publisherDto));

		PublisherDto result = publisherService.getPublisher(1L);

		verify(publisherServiceClient).getPublisher(anyLong());
		assertEquals(publisherDto, result);
	}

	@Test
	void testCreatePublisher() {
		PublisherDto publisherDto = new PublisherDto(1L, "Publisher Name");

		doReturn(ResponseEntity.ok().build()).when(publisherServiceClient).createPublisher(any(PublisherDto.class));

		publisherService.createPublisher(publisherDto);

		verify(publisherServiceClient).createPublisher(any(PublisherDto.class));
	}

	@Test
	void testUpdatePublisher() {
		PublisherDto publisherDto = new PublisherDto(1L, "Publisher Name");

		when(publisherServiceClient.updatePublisher(anyLong(), any(PublisherDto.class))).thenReturn(
			ResponseEntity.ok().build());

		publisherService.updatePublisher(1L, publisherDto);

		verify(publisherServiceClient).updatePublisher(anyLong(), any(PublisherDto.class));
	}

	@Test
	void testDeletePublisher() {
		when(publisherServiceClient.deletePublisher(anyLong())).thenReturn(ResponseEntity.ok().build());

		publisherService.deletePublisher(1L);

		verify(publisherServiceClient).deletePublisher(anyLong());
	}
}
