package com.nhnacademy.bookstorefront.couponpolicy.exception;

import com.nhnacademy.bookstorefront.global.controller.payload.ErrorStatus;
import com.nhnacademy.bookstorefront.global.exception.GlobalException;

import lombok.Getter;

@Getter
public class CouponCategoryIdNullException extends GlobalException {
	public CouponCategoryIdNullException(ErrorStatus errorStatus) {
		super(errorStatus);
	}
}
