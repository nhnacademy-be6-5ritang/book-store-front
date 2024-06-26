package com.nhnacademy.bookstorefront.delivery.dto.request;

import lombok.Builder;

@Builder
public record GetDeliveriesRequest(Long userId) {
}