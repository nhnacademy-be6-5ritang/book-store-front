package com.nhnacademy.bookstorefront.order.dto.request;

public record OrderCheckNonRequest(
	String payerEmail,
	String orderInfoId
) {
}
