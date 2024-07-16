package com.nhnacademy.bookstorefront.deliverystatus.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateDeliveryStatusRequest(
	@NotBlank @Size(max = 10) String deliveryStatusName) {
}
