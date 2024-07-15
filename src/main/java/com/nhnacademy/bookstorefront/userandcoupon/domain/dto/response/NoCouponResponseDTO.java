package com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response;

import java.math.BigDecimal;

public record NoCouponResponseDTO(
	BigDecimal orderPrice,
	BigDecimal discount,
	BigDecimal orderPriceBeforePoint

) {


}