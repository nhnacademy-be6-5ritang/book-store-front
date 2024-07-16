package com.nhnacademy.bookstorefront.bookstatus.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookStatusDto(
	@NotNull Long bookStatusId,
	@NotBlank @Size(max = 10) String bookStatusName) {
}
