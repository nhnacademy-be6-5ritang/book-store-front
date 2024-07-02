package com.nhnacademy.bookstorefront.payment.dto.response;

import com.nhnacademy.bookstorefront.order.dto.response.FindByInfoIdBookOrderGetBookResponse;
import com.nhnacademy.bookstorefront.order.dto.response.FindByInfoIdBookOrderGetOrderResponse;

public record GetBookOrderByInfoIdResponse(
	Long orderListId,
	FindByInfoIdBookOrderGetBookResponse getBookResponse,
	FindByInfoIdBookOrderGetOrderResponse getOrderResponse,
	Integer quantity
) {
}
