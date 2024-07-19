package com.nhnacademy.bookstorefront.order.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateWrappingTypeRequest(
	@NotBlank @Size(max = 20) String paperName,
	@NotBlank @Size(max = 200) String paperContent,
	@NotNull BigDecimal paperPrice
) {

}
