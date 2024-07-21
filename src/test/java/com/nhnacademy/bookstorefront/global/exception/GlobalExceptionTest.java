package com.nhnacademy.bookstorefront.global.exception;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.nhnacademy.bookstorefront.global.controller.payload.ErrorStatus;

class GlobalExceptionTest {

	@Test
	void testGlobalException() {
		ErrorStatus mockErrorStatus = Mockito.mock(ErrorStatus.class);
		Mockito.when(mockErrorStatus.getMessage()).thenReturn("Error message");

		GlobalException exception = new GlobalException(mockErrorStatus);

		ErrorStatus result = exception.getErrorStatus();

		assertThat(result).isEqualTo(mockErrorStatus);
		assertThat(result.getMessage()).isEqualTo("Error message");
	}
}