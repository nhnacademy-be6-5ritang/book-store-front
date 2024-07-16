package com.nhnacademy.bookstorefront.order.dto.response;

import lombok.Builder;

@Builder
public record CreateCartOrderResponse(
	Long orderId,
	String orderInfoId
) {
}
