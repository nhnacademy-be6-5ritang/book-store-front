package com.nhnacademy.bookstorefront.cart.dto.response;

import jakarta.servlet.http.Cookie;

public record CreateCartResponse(
	Cookie cookie,
	Long cardId,
	Long userId) {
}
