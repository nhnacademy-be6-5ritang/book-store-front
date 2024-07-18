package com.nhnacademy.bookstorefront.delivery.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UpdateDeliveryRequest(@NotNull Long deliveryStatusId) {
}
