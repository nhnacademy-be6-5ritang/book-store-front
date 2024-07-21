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

import com.nhnacademy.bookstorefront.order.dto.request.CreateRefundPolicyRequest;
import com.nhnacademy.bookstorefront.order.dto.request.UpdateRefundPolicyRequest;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllRefundResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.Impl.RefundPolicyServiceImpl;

public class RefundPolicyServiceImplTest {

	@Mock
	private OrderServiceClient orderServiceClient;

	@InjectMocks
	private RefundPolicyServiceImpl refundPolicyService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateRefundPolicy() {
		CreateRefundPolicyRequest request = mock(CreateRefundPolicyRequest.class);

		// OrderServiceClient의 createRefundPolicy 메서드는 반환값이 없는 void 메서드입니다.
		when(orderServiceClient.createRefundPolicy(any(CreateRefundPolicyRequest.class)))
			.thenReturn(ResponseEntity.ok().build());

		refundPolicyService.createRefundPolicy(request);

		// createRefundPolicy 메서드가 한 번 호출되었는지 검증합니다.
		verify(orderServiceClient, times(1)).createRefundPolicy(any(CreateRefundPolicyRequest.class));
	}

	@Test
	public void testUpdateRefundPolicy() {
		Long refundPolicyId = 1L;
		UpdateRefundPolicyRequest request = mock(UpdateRefundPolicyRequest.class);

		// OrderServiceClient의 updateRefundPolicy 메서드는 반환값이 없는 void 메서드입니다.
		when(orderServiceClient.updateRefundPolicy(any(Long.class), any(UpdateRefundPolicyRequest.class)))
			.thenReturn(ResponseEntity.ok().build());

		refundPolicyService.updateRefundPolicy(request, refundPolicyId);

		// updateRefundPolicy 메서드가 한 번 호출되었는지 검증합니다.
		verify(orderServiceClient, times(1)).updateRefundPolicy(any(Long.class), any(UpdateRefundPolicyRequest.class));
	}

	@Test
	public void testDeleteRefundPolicy() {
		Long refundPolicyId = 1L;

		// OrderServiceClient의 deleteRefundPolicy 메서드는 반환값이 없는 void 메서드입니다.
		when(orderServiceClient.deleteRefundPolicy(any(Long.class)))
			.thenReturn(ResponseEntity.ok().build());

		refundPolicyService.deleteRefundPolicy(refundPolicyId);

		// deleteRefundPolicy 메서드가 한 번 호출되었는지 검증합니다.
		verify(orderServiceClient, times(1)).deleteRefundPolicy(any(Long.class));
	}

	@Test
	public void testGetAllRefundPolicies() {
		GetAllRefundResponse response = mock(GetAllRefundResponse.class);

		when(orderServiceClient.getRefundPolicy())
			.thenReturn(ResponseEntity.ok(response));

		GetAllRefundResponse result = refundPolicyService.getAllRefundPolicies();

		assertEquals(response, result);
	}
}
