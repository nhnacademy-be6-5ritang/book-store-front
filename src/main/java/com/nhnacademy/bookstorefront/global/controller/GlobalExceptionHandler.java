package com.nhnacademy.bookstorefront.global.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.nhnacademy.bookstorefront.global.controller.payload.ErrorStatus;
import com.nhnacademy.bookstorefront.global.exception.GlobalException;

import feign.FeignException;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author 이경헌
 * 전역 예외 처리를 담당하는 클래스입니다.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

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
			script.println("alert('" + errorStatus.getMessage() + "')");
			script.println("history.back()");
			script.println("</script>");
			script.flush();
			script.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * GlobalException 예외를 처리하고 에러 페이지로 리다이렉트합니다.
	 *
	 * @param exception 발생한 예외 객체
	 * @return 에러 페이지 뷰 이름
	 */
	@ExceptionHandler(value = GlobalException.class)
	public ModelAndView globalHandleException(GlobalException exception) {
		ModelAndView modelAndView = new ModelAndView("global/error");

		ErrorStatus errorStatus = exception.getErrorStatus();
		HttpStatus status = errorStatus.getStatus();
		String message = errorStatus.getMessage();
		LocalDateTime timestamp = errorStatus.getTimestamp();

		modelAndView.addObject("message", message != null ? message : "An unexpected error occurred.");
		modelAndView.addObject("status", status != null ? status.value() : HttpStatus.INTERNAL_SERVER_ERROR.value());
		modelAndView.addObject("timestamp", timestamp != null ? timestamp.toString() : LocalDateTime.now().toString());

		// Set default status for errors not explicitly handled
		if (status == null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		// Customize response based on specific status codes
		switch (status) {
			case BAD_REQUEST:
				modelAndView.setStatus(HttpStatus.BAD_REQUEST);
				break;
			case UNAUTHORIZED:
				modelAndView.setStatus(HttpStatus.UNAUTHORIZED);
				break;
			case FORBIDDEN:
				modelAndView.setStatus(HttpStatus.FORBIDDEN);
				break;
			case NOT_FOUND:
				modelAndView.setStatus(HttpStatus.NOT_FOUND);
				break;
			case CONFLICT:
				modelAndView.setStatus(HttpStatus.CONFLICT);
				break;
			default:
				modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				break;
		}

		return modelAndView;
	}

	/**
	 * FeignException 을 처리하고 상태 코드에 따라 응답을 반환합니다.
	 *
	 * @param exception 발생한 Feign 예외 객체
	 * @return 상태 코드에 따른 ResponseEntity 객체
	 */
	@ExceptionHandler(FeignException.class)
	public ModelAndView handleFeignStatusException(FeignException exception) {
		if (exception.status() == HttpStatus.FORBIDDEN.value()
			|| exception.status() == HttpStatus.UNAUTHORIZED.value()) {
			// 403 예외인 경우 로그인 페이지로 리다이렉트
			return new ModelAndView(new RedirectView("/auth/login"));
		}

		ModelAndView modelAndView = new ModelAndView("global/error");
		modelAndView.addObject("message", exception.getMessage());
		modelAndView.addObject("status", HttpStatus.valueOf(exception.status()));
		modelAndView.addObject("timestamp", LocalDateTime.now());

		return modelAndView;
	}

	/**
	 * Exception 예외를 처리하고 에러 페이지로 리다이렉트합니다.
	 *
	 * @param exception 발생한 예외 객체
	 * @return 에러 페이지 뷰 이름
	 */
	@ExceptionHandler(value = Exception.class)
	public ModelAndView handleException(Exception exception) {
		ModelAndView modelAndView = new ModelAndView("global/error");
		modelAndView.addObject("message", exception.getMessage());
		modelAndView.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR);
		modelAndView.addObject("timestamp", LocalDateTime.now());
		return modelAndView;
	}

}
