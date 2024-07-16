package com.nhnacademy.bookstorefront.deliverypolicy.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateDeliveryPolicyRequest(
	@NotBlank @Size(max = 20) String deliveryPolicyName,
	@NotBlank @Size(max = 200) String deliveryPolicyContent,
	@NotNull BigDecimal deliveryPolicyPrice,
	@NotNull BigDecimal deliveryPolicyStandardPrice) {
}
