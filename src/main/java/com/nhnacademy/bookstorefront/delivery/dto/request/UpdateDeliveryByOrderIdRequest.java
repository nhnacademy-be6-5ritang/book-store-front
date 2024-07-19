package com.nhnacademy.bookstorefront.delivery.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateDeliveryByOrderIdRequest(
	@NotBlank @Size(max = 20) String senderName,
	@NotBlank @Size(max = 40) String senderAddress,
	@NotBlank @Size(max = 60) String senderAddress2,
	@NotBlank @Size(max = 20) String senderPhone
) {
}
