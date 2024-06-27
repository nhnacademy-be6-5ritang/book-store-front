package com.nhnacademy.bookstorefront.order.dto.request;

import java.math.BigDecimal;

public record CreateOrderRequest(
	String payerName,
	String payerEmail,
	String payerNumber,
	String payerAddress,
	BigDecimal orderPrice,
	BigDecimal pointSale,
	BigDecimal couponSale
) {
}
