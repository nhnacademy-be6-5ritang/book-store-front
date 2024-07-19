package com.nhnacademy.bookstorefront.payment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CancelTextRequest(
	@NotBlank @Size(max = 200) String reason
) {
	public static CancelTextRequest form(String reason) {
		return CancelTextRequest.builder()
			.reason(reason)
			.build();
	}
}
