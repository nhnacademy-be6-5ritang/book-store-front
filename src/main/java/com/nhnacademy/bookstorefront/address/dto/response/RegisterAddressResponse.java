package com.nhnacademy.bookstorefront.address.dto.response;

public record RegisterAddressResponse(
	Long id,
	Long userId,
	String postCode,
	String baseAddress,
	String detailAddress,
	String alias
) {
}

