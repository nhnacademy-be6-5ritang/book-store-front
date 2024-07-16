package com.nhnacademy.bookstorefront.bookstatus.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookStatusDto(
	Long bookStatusId,
	@NotBlank @Size(max = 10) String bookStatusName) {
}
