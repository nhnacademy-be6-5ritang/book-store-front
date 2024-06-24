package com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request;

public record UserAndCouponRequestCreateDTO(
	String userEmail,
	Boolean isUsed
) {

}