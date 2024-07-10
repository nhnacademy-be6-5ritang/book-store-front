package com.nhnacademy.bookstorefront.order.dto.response;

import java.math.BigDecimal;


public record GetUserPointOrderResponse(
	BigDecimal userPoint
) {
}
