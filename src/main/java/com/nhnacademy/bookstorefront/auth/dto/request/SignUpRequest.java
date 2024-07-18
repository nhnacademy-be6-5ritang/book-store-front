package com.nhnacademy.bookstorefront.auth.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignUpRequest(
	@NotBlank(message = "이름을 입력해주세요.")
	@Size(max = 10, message = "이름은 10자 이하로 입력해주세요.")
	String name,

	@NotBlank(message = "이메일을 입력해주세요.")
	@Size(max = 30, message = "이메일은 30자 이하로 입력해주세요.")
	String email,

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Size(max = 100, message = "비밀번호는 100자 이하로 입력해주세요.")
	String password,

	@NotNull(message = "생년월일을 입력해주세요.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate birth,

	@NotBlank(message = "휴대폰 번호를 입력해주세요.")
	@Size(min = 11, max = 11, message = "휴대폰 번호는 11자리여야 합니다.")
	String contact
) {
}
