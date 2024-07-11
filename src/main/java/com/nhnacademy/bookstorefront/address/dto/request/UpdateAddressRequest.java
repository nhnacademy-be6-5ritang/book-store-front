package com.nhnacademy.bookstorefront.address.dto.request;

import lombok.Builder;

@Builder
public record UpdateAddressRequest(
	String alias,
	String postCode,
	String baseAddress,
	String detailAddress
) {
}
