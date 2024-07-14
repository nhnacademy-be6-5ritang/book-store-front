package com.nhnacademy.bookstorefront.auth.dto.response;

public record PaycoLoginResponse(
	String accessToken,
	String refreshToken
) {
}
