package com.nhnacademy.bookstorefront.order.dto.response;

public record GetBookOrderResponse(
	GetBookOrderGetBookResponse getBookResponse,
	Integer quantity,
	Long orderListId,
	Long orderId
) {

}
