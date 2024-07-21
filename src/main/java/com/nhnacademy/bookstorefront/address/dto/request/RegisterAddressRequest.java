package com.nhnacademy.bookstorefront.address.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterAddressRequest(
	@NotBlank @Size(max = 30, message = "별칭은 30자 이하로 입력해주세요.")
	String alias,

	@NotBlank @Size(min = 5, max = 5, message = "우편번호는 5자리여야 합니다.")
	String postCode,

	@NotBlank @Size(max = 100, message = "기본 주소는 100자 이하로 입력해주세요.")
	String baseAddress,

	@NotBlank @Size(max = 100, message = "상세 주소는 50자 이하로 입력해주세요.")
	String detailAddress
) {
}
