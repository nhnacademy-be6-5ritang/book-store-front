package com.nhnacademy.bookstorefront.global.controller.payload;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class ErrorStatusTest {

	private final ObjectMapper objectMapper = new ObjectMapper()
		.registerModule(new JavaTimeModule());

	@Test
	void testErrorStatusCreation() {
		String message = "Error occurred";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		LocalDateTime timestamp = LocalDateTime.now();

		ErrorStatus errorStatus = ErrorStatus.from(message, status, timestamp);

		assertThat(errorStatus).isNotNull();
		assertThat(errorStatus.getMessage()).isEqualTo(message);
		assertThat(errorStatus.getStatus()).isEqualTo(status);
		assertThat(errorStatus.getTimestamp()).isEqualTo(timestamp);
	}

	@Test
	void testErrorStatusSerialization() throws Exception {
		LocalDateTime timestamp = LocalDateTime.now();
		ErrorStatus errorStatus = ErrorStatus.from("Error occurred", HttpStatus.BAD_REQUEST, timestamp);

		String json = objectMapper.writeValueAsString(errorStatus);

		assertThat(json).contains("\"message\":\"Error occurred\"");
		assertThat(json).contains("\"status\":\"BAD_REQUEST\"");
		assertThat(json).contains("\"timestamp\"");
	}
	
}
