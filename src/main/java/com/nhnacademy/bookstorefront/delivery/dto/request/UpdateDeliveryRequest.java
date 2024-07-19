package com.nhnacademy.bookstorefront.delivery.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateDeliveryRequest(@NotNull Long deliveryStatusId) {
}
