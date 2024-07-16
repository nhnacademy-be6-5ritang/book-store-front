package com.nhnacademy.bookstorefront.delivery.dto.request;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record CreateDeliveryRequest(
	String deliveryReceiver,
	String deliveryReceiverPhone,
	LocalDateTime deliveryReceiverDate,
	String deliveryReceiverAddress,
	String deliveryReceiverAddress2
) {
}
