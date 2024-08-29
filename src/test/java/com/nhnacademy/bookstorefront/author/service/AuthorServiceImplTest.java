package com.nhnacademy.bookstorefront.author.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.author.dto.response.AuthorDto;
import com.nhnacademy.bookstorefront.author.feignclient.AuthorServiceClient;

class AuthorServiceImplTest {

	private AuthorServiceImpl authorService;

	@Mock
	private AuthorServiceClient authorServiceClient;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		authorService = new AuthorServiceImpl(authorServiceClient);
	}

	@Test
	void testGetAuthorsWithPagination() {
		AuthorDto authorDto = new AuthorDto(1L, "John Doe");
		Page<AuthorDto> authorPage = new PageImpl<>(List.of(authorDto));

		when(authorServiceClient.getAuthors(any(Pageable.class)))
			.thenReturn(new ResponseEntity<>(authorPage, HttpStatus.OK));

		Page<AuthorDto> result = authorService.getAuthors(Pageable.unpaged());

		assertNotNull(result);
		assertEquals(1, result.getContent().size());
		assertEquals("John Doe", result.getContent().get(0).authorName());
	}

	@Test
	void testGetAuthor() {
		Long authorId = 1L;
		AuthorDto authorDto = new AuthorDto(authorId, "John Doe");

		when(authorServiceClient.getAuthor(authorId)).thenReturn(new ResponseEntity<>(authorDto, HttpStatus.OK));

		AuthorDto result = authorService.getAuthor(authorId);

		assertNotNull(result);
		assertEquals(authorId, result.authorId());
		assertEquals("John Doe", result.authorName());
	}

	@Test
	void testCreateAuthor() {
		AuthorDto authorDto = new AuthorDto(null, "John Doe");

		authorService.createAuthor(authorDto);

		verify(authorServiceClient).createAuthor(authorDto);
	}

	@Test
	void testUpdateAuthor() {
		Long authorId = 1L;
		AuthorDto authorDto = new AuthorDto(authorId, "Updated Name");

		authorService.updateAuthor(authorId, authorDto);

		verify(authorServiceClient).updateAuthor(authorId, authorDto);
	}

	@Test
	void testDeleteAuthor() {
		Long authorId = 1L;

		authorService.deleteAuthor(authorId);

		verify(authorServiceClient).deleteAuthor(authorId);
	}
}