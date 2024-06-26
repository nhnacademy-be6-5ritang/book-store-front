package com.nhnacademy.bookstorefront.deliverystatus.dto.response;

import lombok.Builder;

@Builder
public record CreateDeliveryStatusResponse(Long deliveryStatusId, String deliveryStatusName) {
}
