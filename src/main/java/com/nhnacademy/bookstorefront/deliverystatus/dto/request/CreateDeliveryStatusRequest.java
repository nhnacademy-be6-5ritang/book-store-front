package com.nhnacademy.bookstorefront.deliverystatus.dto.request;

import lombok.Builder;

@Builder
public record CreateDeliveryStatusRequest(String deliveryStatusName) {
}
