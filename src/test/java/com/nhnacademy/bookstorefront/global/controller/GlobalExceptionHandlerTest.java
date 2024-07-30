package com.nhnacademy.bookstorefront.global.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstorefront.global.controller.payload.ErrorStatus;
import com.nhnacademy.bookstorefront.global.exception.GlobalException;
import com.nhnacademy.bookstorefront.user.service.UserService;

import feign.Request;
import jakarta.servlet.http.HttpServletResponse;

class GlobalExceptionHandlerTest {

	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@InjectMocks
	private GlobalExceptionHandler globalExceptionHandler;

	@Mock
	private HttpServletResponse response;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new GlobalExceptionHandler())
			.setControllerAdvice(globalExceptionHandler)
			.build();
		objectMapper = new ObjectMapper();
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

	// TODO: FeignException 테스팅 필요
	// @Test
	// void handleFeignStatusException_Unauthorized() throws Exception {
	// 	FeignException feignException = FeignException.errorStatus(
	// 		"Unauthorized",
	// 		feign.Response.builder()
	// 			.status(HttpStatus.UNAUTHORIZED.value())
	// 			.request(Mockito.mock(feign.Request.class))
	// 			.body("{\"message\":\"Unauthorized access\"}", StandardCharsets.UTF_8)
	// 			.build()
	// 	);
	//
	// 	// Mocking the exception handler to throw the exception
	// 	mockMvc.perform(MockMvcRequestBuilders.get("/users/my-page")
	// 			.requestAttr("feignException", feignException)) // Pass the exception as an attribute
	// 		.andExpect(status().isUnauthorized())
	// 		.andExpect(content().contentType("text/html;charset=UTF-8"))
	// 		.andExpect(content().string(org.hamcrest.Matchers.containsString("alert('Unauthorized access')")));
	// }
	//
	// @Test
	// void handleFeignStatusException_Forbidden() throws Exception {
	// 	FeignException feignException = FeignException.errorStatus(
	// 		"Forbidden",
	// 		feign.Response.builder()
	// 			.status(HttpStatus.FORBIDDEN.value())
	// 			.reason("Forbidden")
	// 			.request(Mockito.mock(feign.Request.class))
	// 			.body("{\"message\":\"Forbidden access\"}", StandardCharsets.UTF_8)
	// 			.build()
	// 	);
	//
	// 	doThrow(feignException).when(response).setContentType("text/html;charset=UTF-8");
	//
	// 	mockMvc.perform(MockMvcRequestBuilders.get("/some-url")) // You may need to change this URL to a valid endpoint
	// 		.andExpect(status().isForbidden())
	// 		.andExpect(content().contentType("text/html;charset=UTF-8"))
	// 		.andExpect(content().string(org.hamcrest.Matchers.containsString("alert('Forbidden access')")));
	// }
	//
	// @Test
	// void handleFeignStatusException_Other() throws Exception {
	// 	FeignException feignException = FeignException.errorStatus(
	// 		"Internal Server Error",
	// 		feign.Response.builder()
	// 			.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
	// 			.reason("Internal Server Error")
	// 			.request(Mockito.mock(feign.Request.class))
	// 			.body("{\"message\":\"Internal error occurred\"}", StandardCharsets.UTF_8)
	// 			.build()
	// 	);
	//
	// 	doThrow(feignException).when(response).setContentType("text/html;charset=UTF-8");
	//
	// 	mockMvc.perform(MockMvcRequestBuilders.get("/some-url")) // You may need to change this URL to a valid endpoint
	// 		.andExpect(status().isInternalServerError())
	// 		.andExpect(view().name("global/error"))
	// 		.andExpect(model().attribute("message", "Internal error occurred"))
	// 		.andExpect(model().attribute("status", HttpStatus.INTERNAL_SERVER_ERROR))
	// 		.andExpect(model().attribute("timestamp", org.hamcrest.Matchers.notNullValue()));
	// }

	private feign.Response createResponse(HttpStatus status) {
		return feign.Response.builder()
			.status(status.value())
			.request(
				Request.create(Request.HttpMethod.GET, "http://example.com", Collections.emptyMap(), null, null, null))
			.build();
	}
}
