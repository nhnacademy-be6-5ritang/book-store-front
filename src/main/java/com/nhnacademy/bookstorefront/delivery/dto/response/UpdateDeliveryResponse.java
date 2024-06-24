package com.nhnacademy.bookstorefront.delivery.dto.response;

import lombok.Builder;

@Builder
public record UpdateDeliveryResponse(Long deliveryId, Long orderId, String deliveryStatusName) {
}
