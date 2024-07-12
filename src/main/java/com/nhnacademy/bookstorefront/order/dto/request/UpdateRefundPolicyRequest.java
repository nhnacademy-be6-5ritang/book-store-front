package com.nhnacademy.bookstorefront.order.dto.request;

public record UpdateRefundPolicyRequest(
	String refundPolicyContent,
	int refundPolicyDate
) {
}
