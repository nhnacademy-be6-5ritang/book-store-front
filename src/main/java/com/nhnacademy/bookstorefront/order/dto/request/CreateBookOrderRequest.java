package com.nhnacademy.bookstorefront.order.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateBookOrderRequest(
	@NotNull Long bookId,
	@NotNull Long orderId,
	@NotNull Integer quantity
) {
}
