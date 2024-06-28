package com.nhnacademy.bookstorefront.order.dto.response;

public record CreateBookOrderResponse(
	CreateBookOrderGetBookResponse getBookResponse,
	CreateBookOrderGetOrderResponse getOrderResponse,
	Integer quantity,
	Long orderListId
) {
}
