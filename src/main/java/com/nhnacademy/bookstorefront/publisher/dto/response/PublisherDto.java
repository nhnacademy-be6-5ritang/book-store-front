package com.nhnacademy.bookstorefront.publisher.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PublisherDto(
	Long publisherId,
	@NotBlank @Size(max = 100) String publisherName) {
}
