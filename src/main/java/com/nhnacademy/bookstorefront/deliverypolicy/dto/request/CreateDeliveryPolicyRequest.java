package com.nhnacademy.bookstorefront.deliverypolicy.dto.request;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record CreateDeliveryPolicyRequest(String deliveryPolicyName,
										  BigDecimal deliveryPolicyPrice, String deliveryPolicyContent) {
}
