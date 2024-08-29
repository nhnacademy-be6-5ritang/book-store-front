package com.nhnacademy.bookstorefront.author.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.author.dto.response.AuthorDto;
import com.nhnacademy.bookstorefront.author.service.impl.AuthorServiceImpl;

class AuthorControllerTest {

	private MockMvc mockMvc;

	@Mock
	private AuthorServiceImpl authorService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new AuthorController(authorService))
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
	}

	@Test
	void testCreateAuthorForm() throws Exception {
		mockMvc.perform(get("/api/authors/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("author/create-author"));
	}

	@Test
	void testUpdateAuthorForm() throws Exception {
		Long authorId = 1L;
		AuthorDto authorDto = new AuthorDto(authorId, "John Doe");
		when(authorService.getAuthor(authorId)).thenReturn(authorDto);

		mockMvc.perform(get("/api/authors/update/{authorId}", authorId))
			.andExpect(status().isOk())
			.andExpect(view().name("author/update-author"))
			.andExpect(model().attribute("author", authorDto));
	}

	@Test
	void testGetAuthorsWithPagination() throws Exception {
		AuthorDto authorDto = new AuthorDto(1L, "John Doe");
		Page<AuthorDto> authorPage = new PageImpl<>(List.of(authorDto));

		when(authorService.getAuthors(any(Pageable.class))).thenReturn(authorPage);

		mockMvc.perform(get("/api/authors/page?page=1&size=10"))
			.andExpect(status().isOk())
			.andExpect(view().name("author/list-author"))
			.andExpect(model().attribute("authors", authorPage));
	}

	@Test
	void testCreateAuthor() throws Exception {
		AuthorDto authorDto = new AuthorDto(null, "John Doe");

		mockMvc.perform(post("/api/authors")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("authorName", authorDto.authorName()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/authors/page"));

		verify(authorService).createAuthor(any(AuthorDto.class));
	}

	@Test
	void testUpdateAuthor() throws Exception {
		Long authorId = 1L;
		AuthorDto authorDto = new AuthorDto(authorId, "Updated Name");

		doNothing().when(authorService).updateAuthor(eq(authorId), any(AuthorDto.class));

		mockMvc.perform(put("/api/authors/{authorId}", authorId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("authorName", authorDto.authorName()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/authors/page"));

		verify(authorService).updateAuthor(eq(authorId), any(AuthorDto.class));
	}

	@Test
	void testDeleteAuthor() throws Exception {
		Long authorId = 1L;

		mockMvc.perform(delete("/api/authors/{authorId}", authorId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/authors/page"));

		verify(authorService).deleteAuthor(authorId);
	}
}