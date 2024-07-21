package com.nhnacademy.bookstorefront.author.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.nhnacademy.bookstorefront.author.dto.response.AuthorDto;

class AuthorDtoTest {

	@Test
	void testAuthorDtoConstructorAndGetters() {
		Long authorId = 1L;
		String authorName = "John Doe";

		AuthorDto authorDto = new AuthorDto(authorId, authorName);

		assertThat(authorDto.authorId()).isEqualTo(authorId);
		assertThat(authorDto.authorName()).isEqualTo(authorName);
	}
}