package com.nhnacademy.bookstorefront.bookcart.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateBookCartRequest(
	@NotNull Integer bookQuantity) {
}
