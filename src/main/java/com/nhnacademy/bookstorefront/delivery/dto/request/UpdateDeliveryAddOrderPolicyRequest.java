package com.nhnacademy.bookstorefront.delivery.dto.request;

public record UpdateDeliveryAddOrderPolicyRequest(
	Long deliveryId,
	Long orderId,
	Long deliveryPolicyId
) {
}
