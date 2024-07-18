package com.nhnacademy.bookstorefront.global.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.global.controller.payload.ErrorStatus;

import feign.FeignException;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author 이경헌
 * 전역 예외 처리를 담당하는 클래스입니다.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * FeignException 을 처리하고 상태 코드에 따라 응답을 반환합니다.
	 *
	 * @param exception 발생한 Feign 예외 객체
	 * @param model     예외 메시지를 저장할 모델 객체
	 * @return 상태 코드에 따른 ResponseEntity 객체
	 */
	@ExceptionHandler(FeignException.class)
	public ModelAndView handleFeignStatusException(FeignException exception, Model model) {
		ModelAndView modelAndView = new ModelAndView("global/error");
		modelAndView.addObject("message", exception.getMessage());

		if (exception.status() >= 500 && exception.status() < 600) {
			modelAndView.setStatus(HttpStatus.valueOf(exception.status())); // 기타 상태 코드 설정
		} else {
			// TODO: [김다운] alert로 띄우기
		}

		return modelAndView;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public void processValidationError(MethodArgumentNotValidException exception, HttpServletResponse response) {
		BindingResult bindingResult = exception.getBindingResult();
		StringBuilder errorMessageBuilder = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errorMessageBuilder
				.append("필드 : ")
				.append(fieldError.getField())
				.append("\\n")
				.append("에러: ")
				.append(fieldError.getDefaultMessage());

		}

		// 오류 메시지 생성
		String errorMessage = errorMessageBuilder.toString();

		// ErrorStatus 객체 생성
		ErrorStatus errorStatus = ErrorStatus.from(
			errorMessage,
			HttpStatus.BAD_REQUEST,
			LocalDateTime.now()
		);

		PrintWriter script;

		response.setContentType("text/html;charset=UTF-8");
		try {
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('"+ errorStatus.getMessage() + "')");
			script.println("history.back()");
			script.println("</script>");
			script.flush();
			script.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}

	/**
	 * 예외를 처리하고 에러 페이지로 리다이렉트합니다.
	 *
	 * @param exception 발생한 예외 객체
	 * @param model     예외 메시지를 저장할 모델 객체
	 * @return 에러 페이지 뷰 이름
	 */
	@ExceptionHandler(value = Exception.class)
	public ModelAndView handleException(Exception exception, Model model) {
		ModelAndView modelAndView = new ModelAndView("global/error");
		modelAndView.addObject("message", exception.getMessage());
		modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR); // 기본 상태 코드를 500으로 설정
		return modelAndView;
	}

}
