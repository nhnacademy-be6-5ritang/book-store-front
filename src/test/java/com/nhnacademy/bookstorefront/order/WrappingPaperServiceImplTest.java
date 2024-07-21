package com.nhnacademy.bookstorefront.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.order.dto.response.GetListWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetWrappingResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.Impl.WrappingPaperServiceImpl;

public class WrappingPaperServiceImplTest {

	@Mock
	private OrderServiceClient orderServiceClient;

	@InjectMocks
	private WrappingPaperServiceImpl wrappingPaperService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateWrappingPapers() {
		Long paperId = 1L;
		Long bookOrderId = 2L;
		Integer quantity = 5;

		GetWrappingResponse response = mock(GetWrappingResponse.class);

		when(orderServiceClient.createWrappingPapers(any(Long.class), any(Long.class), any(Integer.class)))
			.thenReturn(ResponseEntity.ok(response));

		GetWrappingResponse result = wrappingPaperService.createWrappingPapers(paperId, bookOrderId, quantity);

		assertEquals(response, result);
	}

	@Test
	public void testGetWrappingPaperByOrderListId() {
		Long id = 1L;

		GetListWrappingResponse response = mock(GetListWrappingResponse.class);

		when(orderServiceClient.getWrappingPaperByOrderListId(any(Long.class)))
			.thenReturn(ResponseEntity.ok(response));

		GetListWrappingResponse result = wrappingPaperService.getWrappingPaperByOrderListId(id);

		assertEquals(response, result);
	}
}
