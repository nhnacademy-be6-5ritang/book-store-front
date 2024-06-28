package com.nhnacademy.bookstorefront.deliverypolicy.dto.response;

import java.math.BigDecimal;

public record CreateDeliveryPolicyResponse(
	Long deliveryPolicyId,
	String deliveryPolicyName,
	BigDecimal deliveryPolicyPrice,
	String deliveryPolicyContent,
	BigDecimal deliveryPolicyStandardPrice) {
}
