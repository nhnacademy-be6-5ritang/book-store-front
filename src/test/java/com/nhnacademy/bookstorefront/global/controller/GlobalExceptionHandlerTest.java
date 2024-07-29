package com.nhnacademy.bookstorefront.global.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.global.controller.payload.ErrorStatus;
import com.nhnacademy.bookstorefront.global.exception.GlobalException;

import feign.FeignException;
import feign.Request;
import jakarta.servlet.http.HttpServletResponse;

class GlobalExceptionHandlerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private GlobalExceptionHandler globalExceptionHandler;

	@Mock
	private HttpServletResponse response;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new GlobalExceptionHandler())
			.setControllerAdvice(globalExceptionHandler)
			.build();
	}

	@Test
	void testProcessValidationError() throws Exception {
		BindingResult bindingResult = mock(BindingResult.class);
		FieldError fieldError = new FieldError("objectName", "fieldName", "defaultMessage");
		when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
		MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

		PrintWriter writer = mock(PrintWriter.class);
		when(response.getWriter()).thenReturn(writer);

		globalExceptionHandler.processValidationError(exception, response);

		verify(writer, times(1)).println("<script>");
		verify(writer, times(1)).println("alert('필드 : fieldName\\n에러: defaultMessage')");
		verify(writer, times(1)).println("history.back()");
		verify(writer, times(1)).println("</script>");
		verify(writer, times(1)).flush();
		verify(writer, times(1)).close();
	}

	@Test
	void testGlobalHandleException() {
		LocalDateTime now = LocalDateTime.now();
		ErrorStatus errorStatus = ErrorStatus.from("Test Error", HttpStatus.BAD_REQUEST, now);
		GlobalException exception = new GlobalException(errorStatus);

		ModelAndView modelAndView = globalExceptionHandler.globalHandleException(exception);

		assert modelAndView.getViewName().equals("global/error");
		assert modelAndView.getModel().get("message").equals("Test Error");
		assert modelAndView.getModel().get("status").equals(HttpStatus.BAD_REQUEST.value());
		assert modelAndView.getModel().get("timestamp").equals(now.toString());
	}

	@Test
	void testGlobalHandleExceptionBadRequest() {
		testGlobalHandleException(HttpStatus.BAD_REQUEST, "Test Error", "global/error");
	}

	@Test
	void testGlobalHandleExceptionUnauthorized() {
		testGlobalHandleException(HttpStatus.UNAUTHORIZED, "Test Error", "global/error");
	}

	@Test
	void testGlobalHandleExceptionForbidden() {
		testGlobalHandleException(HttpStatus.FORBIDDEN, "Test Error", "global/error");
	}

	@Test
	void testGlobalHandleExceptionNotFound() {
		testGlobalHandleException(HttpStatus.NOT_FOUND, "Test Error", "global/error");
	}

	@Test
	void testGlobalHandleExceptionConflict() {
		testGlobalHandleException(HttpStatus.CONFLICT, "Test Error", "global/error");
	}

	@Test
	void testGlobalHandleExceptionInternalServerError() {
		testGlobalHandleException(HttpStatus.INTERNAL_SERVER_ERROR, "Test Error", "global/error");
	}

	private void testGlobalHandleException(HttpStatus status, String message, String viewName) {
		LocalDateTime now = LocalDateTime.now();
		ErrorStatus errorStatus = ErrorStatus.from(message, status, now);
		GlobalException exception = new GlobalException(errorStatus);

		ModelAndView modelAndView = globalExceptionHandler.globalHandleException(exception);

		assertEquals(viewName, modelAndView.getViewName());
		assertEquals(message, modelAndView.getModel().get("message"));
		assertEquals(status.value(), modelAndView.getModel().get("status"));
		assertEquals(now.toString(), modelAndView.getModel().get("timestamp"));
		assertEquals(status, modelAndView.getStatus());
	}

	@Test
	void testHandleRetryableException() {

		ModelAndView modelAndView = globalExceptionHandler.handleRetryableException();

		assert modelAndView.getViewName().equals("global/error");
		assert modelAndView.getModel().get("message").equals("게이트웨이 요청 처리 중 문제가 발생했습니다. 잠시 후 다시 시도해 주세요.");
		assert modelAndView.getModel().get("status").equals(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Test
	void testHandleException() {
		Exception exception = new Exception("Test Exception");

		ModelAndView modelAndView = globalExceptionHandler.handleException(exception);

		assert modelAndView.getViewName().equals("global/error");
		assert modelAndView.getModel().get("message").equals("Test Exception");
		assert modelAndView.getModel().get("status").equals(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	void testHandleFeignStatusExceptionForbidden() throws IOException {
		FeignException exception = FeignException.errorStatus(
			"methodKey",
			createResponse(HttpStatus.FORBIDDEN)
		);

		PrintWriter writer = mock(PrintWriter.class);
		when(response.getWriter()).thenReturn(writer);

		ModelAndView modelAndView = globalExceptionHandler.handleFeignStatusException(exception, response);

		assertNull(modelAndView);
		verify(writer, times(1)).println("<script>");
		verify(writer, times(1)).println("alert('해당 리소스에 대한 접근 권한이 없습니다.')");
		verify(writer, times(1)).println("history.back()");
		verify(writer, times(1)).println("</script>");
		verify(writer, times(1)).flush();
		verify(writer, times(1)).close();
	}

	@Test
	void testHandleFeignStatusExceptionUnauthorized() throws IOException {
		FeignException exception = FeignException.errorStatus(
			"methodKey",
			createResponse(HttpStatus.UNAUTHORIZED)
		);

		PrintWriter writer = mock(PrintWriter.class);
		when(response.getWriter()).thenReturn(writer);

		ModelAndView modelAndView = globalExceptionHandler.handleFeignStatusException(exception, response);

		assertNull(modelAndView);
		verify(writer, times(1)).println("<script>");
		verify(writer, times(1)).println("alert('로그인이 필요합니다');");
		verify(writer, times(1)).println("window.location.href = '/auth/login';");
		verify(writer, times(1)).println("</script>");
		verify(writer, times(1)).flush();
		verify(writer, times(1)).close();
	}

	@Test
	void testHandleFeignStatusExceptionOther() {
		FeignException exception = FeignException.errorStatus(
			"methodKey",
			createResponse(HttpStatus.INTERNAL_SERVER_ERROR)
		);

		ModelAndView modelAndView = globalExceptionHandler.handleFeignStatusException(exception, response);

		assertNotNull(modelAndView);
		assertEquals("global/error", modelAndView.getViewName());
		assertEquals(exception.getMessage(), modelAndView.getModel().get("message"));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, modelAndView.getModel().get("status"));
		assertNotNull(modelAndView.getModel().get("timestamp"));
	}

	private feign.Response createResponse(HttpStatus status) {
		return feign.Response.builder()
			.status(status.value())
			.request(
				Request.create(Request.HttpMethod.GET, "http://example.com", Collections.emptyMap(), null, null, null))
			.build();
	}
}
