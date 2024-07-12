package com.nhnacademy.bookstorefront.order.dto.response;

import lombok.Builder;

@Builder
public record GetRefundResponse(
	Long refundPolicyId,
	String refundPolicyContent,
	int refundPolicyDate
) {
}
