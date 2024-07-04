package com.nhnacademy.bookstorefront.auth.dto.request;

public record LoginRequest(
	String email,
	String password
) {
}
