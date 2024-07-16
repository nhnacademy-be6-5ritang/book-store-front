package com.nhnacademy.bookstorefront.author.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorDto(
	Long authorId,
	@NotBlank @Size(max = 200) String authorName) {
}
