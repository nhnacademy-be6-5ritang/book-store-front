package com.nhnacademy.bookstorefront.order.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetOrderByInfoResponse(
	String infoId,
	String payername,
	String payerEmail,
	String payerAddress,
	LocalDateTime orderDate,
	String status,
	BigDecimal price
) {

}
