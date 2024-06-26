package com.nhnacademy.bookstorefront.deliverystatus.dto.response;

import lombok.Builder;

@Builder
public record GetDeliveryStatusResponse(Long deliveryStatusId, String deliveryStatusName) {
}
