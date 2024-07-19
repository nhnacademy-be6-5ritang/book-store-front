package com.nhnacademy.bookstorefront.order.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateBookOrderRequest(
	@NotNull Long bookId,
	Long orderId,
	@NotNull Integer quantity
) {
}
