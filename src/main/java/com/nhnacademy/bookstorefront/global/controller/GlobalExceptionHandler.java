package com.nhnacademy.bookstorefront.global.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception exception, Model model) {
		model.addAttribute("message", exception.getMessage());
		return "global/error";
	}

}
