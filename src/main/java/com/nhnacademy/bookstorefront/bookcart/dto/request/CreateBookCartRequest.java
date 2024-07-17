package com.nhnacademy.bookstorefront.bookcart.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateBookCartRequest(
	@NotNull Long bookId,
	@NotNull Integer bookQuantity) {
}
