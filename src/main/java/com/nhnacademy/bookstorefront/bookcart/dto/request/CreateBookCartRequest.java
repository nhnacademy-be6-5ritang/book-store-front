package com.nhnacademy.bookstorefront.bookcart.dto.request;

public record CreateBookCartRequest(
	Long bookId,
	Integer bookQuantity) {
}
