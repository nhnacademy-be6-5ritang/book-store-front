package com.nhnacademy.bookstorefront.address.dto.response;

public record UpdateAddressResponse(
	Long id,
	Long userId,
	String postCode,
	String baseAddress,
	String detailAddress,
	String alias
) {
}

