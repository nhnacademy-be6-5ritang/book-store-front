package com.nhnacademy.bookstorefront.global.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 전역 예외 처리를 담당하는 클래스입니다.
 *
 * @version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 예외를 처리하고 에러 페이지로 리다이렉트합니다.
	 *
	 * @param exception 발생한 예외 객체
	 * @param model     예외 메시지를 저장할 모델 객체
	 * @return 에러 페이지 뷰 이름
	 */
	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception exception, Model model) {
		model.addAttribute("message", exception.getMessage());
		return "global/error";
	}

}
