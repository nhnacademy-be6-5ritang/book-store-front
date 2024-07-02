package com.nhnacademy.bookstorefront.order.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record GetOrderByInfoResponse(
	Long orderId,
	String infoId,
	String payername,
	String payerEmail,
	String payerAddress,
	LocalDateTime orderDate,
	String status,
	BigDecimal price
) {
}