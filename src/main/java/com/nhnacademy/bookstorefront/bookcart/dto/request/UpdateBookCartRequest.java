package com.nhnacademy.bookstorefront.bookcart.dto.request;

public record UpdateBookCartRequest(
	Long bookId,
	Long cartId,
	int bookQuantity) {
}
