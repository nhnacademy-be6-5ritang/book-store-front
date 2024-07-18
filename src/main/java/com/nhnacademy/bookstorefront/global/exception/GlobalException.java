package com.nhnacademy.bookstorefront.global.exception;

import com.nhnacademy.bookstorefront.global.controller.payload.ErrorStatus;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
	private final ErrorStatus errorStatus;

	public GlobalException(ErrorStatus errorStatus) {
		this.errorStatus = errorStatus;
	}
}
