package com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request;

public record UserAndCouponRequestCreateDTO(
	Long userId,
	Boolean isUsed
) {

}
