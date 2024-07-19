package com.nhnacademy.bookstorefront.delivery.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateDeliveryAddOrderPolicyRequest(
	@NotNull Long deliveryId,
	@NotNull Long orderId,
	@NotNull Long deliveryPolicyId
) {
}
