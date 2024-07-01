package com.nhnacademy.bookstorefront.payment.dto.response;

import java.math.BigDecimal;

public record TransactionsResponse(
	String orderId,
	String orderName,
	BigDecimal amount,
	String status,
	String provider
) {
}
