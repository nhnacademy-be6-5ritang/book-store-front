package com.nhnacademy.bookstorefront.auth.dto.response;

public record LoginResponse(
	String accessToken,
	String refreshToken
) {
}
