package com.nhnacademy.bookstorefront.tag.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TagDto(
	Long tagId,
	@NotBlank @Size(max = 20) String tagName) {
}
