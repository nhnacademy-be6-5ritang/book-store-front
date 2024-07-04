package com.nhnacademy.bookstorefront.auth.dto.request;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record SignUpRequest(
	String name,
	String email,
	String password,
	LocalDate birth,
	String contact
) {
}
