package com.nhnacademy.bookstorefront.order.dto.request;

public record CreateRefundPolicyRequest(
	String refundPolicyContent,
	int refundPolicyDate
) {
}
