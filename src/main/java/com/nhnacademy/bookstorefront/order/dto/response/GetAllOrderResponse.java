package com.nhnacademy.bookstorefront.order.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record GetAllOrderResponse(
	Long orderId,
	LocalDateTime orderDate,
	BigDecimal orderPrice,
	String orderInfoId,
	String name
) {
}
