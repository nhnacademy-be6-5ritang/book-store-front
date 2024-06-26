package com.nhnacademy.bookstorefront.deliverystatus.dto.response;

import lombok.Builder;

@Builder
public record UpdateDeliveryStatusResponse(Long deliveryStatusId, String deliveryStatusName) {
}
