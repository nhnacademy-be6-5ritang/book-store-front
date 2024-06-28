package com.nhnacademy.bookstorefront.order.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateBookOrderGetOrderResponse(
	String infoId,
	BigDecimal orderPrice,
	LocalDateTime orderDate,
	BigDecimal pointSale,
	BigDecimal couponSale
) {
}
