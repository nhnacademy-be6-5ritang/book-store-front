package com.nhnacademy.bookstorefront.user.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserInfoRequest(
	@NotBlank
	@Size(max = 10)
	String name,
	@Size(min = 8, max = 100)
	String password,
	@NotNull
	LocalDate birth,
	@NotBlank
	@Size(min = 11, max = 11)
	String contact
) {
}
