package com.nhnacademy.bookstorefront.order.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record CreateOrderListPost(
	@NotNull List<Long> paperId
) {
}
