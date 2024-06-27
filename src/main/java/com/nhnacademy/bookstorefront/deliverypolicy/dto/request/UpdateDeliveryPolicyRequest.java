package com.nhnacademy.bookstorefront.deliverypolicy.dto.request;

import java.math.BigDecimal;

public record UpdateDeliveryPolicyRequest(
	String deliveryPolicyName,
	String deliveryPolicyContent,
	BigDecimal deliveryPolicyPrice,
	BigDecimal deliveryPolicyStandardPrice) {
}
