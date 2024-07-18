package com.nhnacademy.bookstorefront.order.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateOrderRequest(
	@NotBlank @Size(max = 10) String payerName,
	@NotBlank @Size(max = 30) String payerEmail,
	@NotBlank @Size(max = 11) String payerNumber,
	@NotBlank @Size(max = 100) String payerAddress,
	@NotNull BigDecimal orderPrice,
	@NotNull BigDecimal pointSale,
	@NotNull BigDecimal couponSale
) {
}
