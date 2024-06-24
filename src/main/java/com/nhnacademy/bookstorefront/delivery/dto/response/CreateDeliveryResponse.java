package com.nhnacademy.bookstorefront.delivery.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record CreateDeliveryResponse(
	Long deliveryId,
	String deliverySenderName,
	String deliverySenderPhone,
	LocalDateTime deliverySenderDate,
	String deliverySenderAddress,
	String deliveryReceiver,
	String deliveryReceiverPhone,
	LocalDateTime deliveryReceiverDate,
	String deliveryReceiverAddress,
	Long orderId,
	Long deliveryStatusId
) {
}