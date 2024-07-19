package com.nhnacademy.bookstorefront.order.dto.response;

import lombok.Builder;

@Builder
public record GetOrderStatusResponse(
	Long orderStatusId,
	String orderStatusName
) {
}
