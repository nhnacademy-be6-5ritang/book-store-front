package com.nhnacademy.bookstorefront.payment.dto.request;

import lombok.Builder;

@Builder
public record CancelTextRequest(
	String reason
) {
	public static CancelTextRequest form(String reason) {
		return CancelTextRequest.builder()
			.reason(reason)
			.build();
	}
}
