package com.nhnacademy.bookstorefront.point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.point.dto.response.GetAllPointTransactionResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointTransactionResponse;
import com.nhnacademy.bookstorefront.point.feignclient.PointServiceClient;
import com.nhnacademy.bookstorefront.point.service.impl.PointTransactionServiceImpl;

class PointTransactionServiceImplTest {

	@Mock
	private PointServiceClient pointServiceClient;

	@InjectMocks
	private PointTransactionServiceImpl pointTransactionService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetPointTransactions() {
		Pageable pageable = PageRequest.of(0, 10);
		List<GetPointTransactionResponse> transactions = List.of(
			new GetPointTransactionResponse("Policy Type", BigDecimal.valueOf(100), LocalDateTime.now())
		);
		Page<GetPointTransactionResponse> response = new PageImpl<>(transactions, pageable, transactions.size());

		when(pointServiceClient.getPointTransactions(any(Pageable.class))).thenReturn(ResponseEntity.ok(response));

		Page<GetPointTransactionResponse> result = pointTransactionService.getPointTransactions(pageable);

		assertEquals(response, result);
	}

	@Test
	void testGetAllPointTransactions() {
		Pageable pageable = PageRequest.of(0, 10);
		List<GetAllPointTransactionResponse> transactions = List.of(
			new GetAllPointTransactionResponse(1L, 1L, BigDecimal.valueOf(100), LocalDateTime.now())
		);
		Page<GetAllPointTransactionResponse> response = new PageImpl<>(transactions, pageable, transactions.size());

		when(pointServiceClient.getAllPointTransactions(any(Pageable.class))).thenReturn(ResponseEntity.ok(response));

		Page<GetAllPointTransactionResponse> result = pointTransactionService.getAllPointTransactions(pageable);

		assertEquals(response, result);
	}
}
