package com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response;

import java.math.BigDecimal;

public record OneCouponResponseDTO(
	BigDecimal orderPrice,
	BigDecimal discount,
	BigDecimal orderPriceAfterCoupon,
	BigDecimal orderPriceBeforePoint

) {


}