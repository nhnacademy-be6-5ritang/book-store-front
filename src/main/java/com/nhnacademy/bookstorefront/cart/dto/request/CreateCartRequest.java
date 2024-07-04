package com.nhnacademy.bookstorefront.cart.dto.request;

import jakarta.servlet.http.Cookie;

public record CreateCartRequest(
	Cookie[] cookies) {
}
