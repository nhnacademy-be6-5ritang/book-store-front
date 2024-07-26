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
import feign.RetryableException;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author 이경헌
 * 전역 예외 처리를 담당하는 클래스입니다.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 검증 오류(MethodArgumentNotValidException)를 처리합니다.
	 *
	 * @param exception 검증 오류 예외 객체
	 * @param response HTTP 응답 객체
	 */
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
	 * GlobalException 예외를 처리하고 에러 페이지를 반환합니다.
	 *
	 * @param exception 발생한 GlobalException 예외 객체
	 * @return 에러 페이지를 표시하는 ModelAndView 객체
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

		if (status == null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

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
	 * @param exception 발생한 FeignException 예외 객체
	 * @return 상태 코드에 따른 ModelAndView 객체
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
	 * RetryableException 을 처리하고 서비스 불가 상태 코드를 반환합니다.
	 *
	 * @return 서비스 불가 상태를 표시하는 ModelAndView 객체
	 */
	@ExceptionHandler(RetryableException.class)
	public ModelAndView handleRetryableException() {
		ModelAndView modelAndView = new ModelAndView("global/error");
		modelAndView.addObject("message", "게이트웨이 요청 처리 중 문제가 발생했습니다. 잠시 후 다시 시도해 주세요.");
		modelAndView.addObject("status", HttpStatus.SERVICE_UNAVAILABLE);
		modelAndView.addObject("timestamp", LocalDateTime.now());

		modelAndView.setStatus(HttpStatus.SERVICE_UNAVAILABLE);

		return modelAndView;
	}

	/**
	 * 일반적인 Exception 을 처리하고 에러 페이지를 반환합니다.
	 *
	 * @param exception 발생한 Exception 예외 객체
	 * @return 에러 페이지를 표시하는 ModelAndView 객체
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
