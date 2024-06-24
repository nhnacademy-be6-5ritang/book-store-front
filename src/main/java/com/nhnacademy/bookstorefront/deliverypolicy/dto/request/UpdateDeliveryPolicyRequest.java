package com.nhnacademy.bookstorefront.deliverypolicy.dto.request;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record UpdateDeliveryPolicyRequest(String deliveryPolicyName,
										  BigDecimal deliveryPolicyPrice, String deliveryPolicyContent) {
}
