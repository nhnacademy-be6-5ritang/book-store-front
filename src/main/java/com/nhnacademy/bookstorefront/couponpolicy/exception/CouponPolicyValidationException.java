package com.nhnacademy.bookstorefront.couponpolicy.exception;

import com.nhnacademy.bookstorefront.global.controller.payload.ErrorStatus;
import com.nhnacademy.bookstorefront.global.exception.GlobalException;

import lombok.Getter;

@Getter
public class CouponPolicyValidationException extends GlobalException {
	public CouponPolicyValidationException(ErrorStatus errorStatus) {
		super(errorStatus);
	}
}
