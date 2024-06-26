package com.nhnacademy.bookstorefront.delivery.dto.request;

import lombok.Builder;

@Builder
public record UpdateDeliveryRequest(Long deliveryStatusId) {
}