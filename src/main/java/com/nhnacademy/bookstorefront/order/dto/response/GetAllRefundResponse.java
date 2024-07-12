package com.nhnacademy.bookstorefront.order.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record GetAllRefundResponse(
	List<GetRefundResponse> refunds
) {
}
