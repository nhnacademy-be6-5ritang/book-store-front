package com.nhnacademy.bookstorefront.order.dto.request;

public record CreateBookOrderRequest(
	Long bookId,
	Long orderId,
	Integer quantity
) {
}
