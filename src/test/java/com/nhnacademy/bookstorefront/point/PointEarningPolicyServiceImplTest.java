package com.nhnacademy.bookstorefront.point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.nhnacademy.bookstorefront.point.dto.request.CreatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.request.UpdatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.response.CreatePointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointTransactionResponse;
import com.nhnacademy.bookstorefront.point.dto.response.UpdatePointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.feignclient.PointServiceClient;
import com.nhnacademy.bookstorefront.point.service.impl.PointEarningPolicyServiceImpl;

class PointEarningPolicyServiceImplTest {

	@Mock
	private PointServiceClient pointServiceClient;

	@InjectMocks
	private PointEarningPolicyServiceImpl pointEarningPolicyService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreatePointEarningPolicy() {
		CreatePointEarningPolicyRequest request = new CreatePointEarningPolicyRequest("Policy Name", BigDecimal.valueOf(10));
		CreatePointEarningPolicyResponse response = new CreatePointEarningPolicyResponse("Policy Name", BigDecimal.valueOf(10));

		when(pointServiceClient.createPointEarningPolicy(any(CreatePointEarningPolicyRequest.class)))
			.thenReturn(ResponseEntity.ok(response));

		CreatePointEarningPolicyResponse result = pointEarningPolicyService.createPointEarningPolicy(request);

		assertEquals(response, result);
	}

	@Test
	void testGetPointEarningPolicies() {
		List<GetPointEarningPolicyResponse> response = List.of(
			new GetPointEarningPolicyResponse(1L, "Policy Name", BigDecimal.valueOf(10), "ACTIVE")
		);

		when(pointServiceClient.getPointEarningPolicies()).thenReturn(ResponseEntity.ok(response));

		List<GetPointEarningPolicyResponse> result = pointEarningPolicyService.getPointEarningPolicies();

		assertEquals(response, result);
	}

	@Test
	void testUpdatePointEarningPolicy() {
		Long policyId = 1L;
		UpdatePointEarningPolicyRequest request = new UpdatePointEarningPolicyRequest("Updated Policy Name", BigDecimal.valueOf(15));
		UpdatePointEarningPolicyResponse response = new UpdatePointEarningPolicyResponse("Updated Policy Name", BigDecimal.valueOf(15));

		when(pointServiceClient.updatePointEarningPolicy(anyLong(), any(UpdatePointEarningPolicyRequest.class)))
			.thenReturn(ResponseEntity.ok(response));

		UpdatePointEarningPolicyResponse result = pointEarningPolicyService.updatePointEarningPolicy(policyId, request);

		assertEquals(response, result);
	}

	@Test
	void testActivatePointEarningPolicy() {
		Long policyId = 1L;

		when(pointServiceClient.activatePointEarningPolicy(anyLong())).thenReturn(ResponseEntity.ok().build());

		pointEarningPolicyService.activatePointEarningPolicy(policyId);
	}

	@Test
	void testDeactivatePointEarningPolicy() {
		Long policyId = 1L;

		when(pointServiceClient.deactivatePointEarningPolicy(anyLong())).thenReturn(ResponseEntity.ok().build());

		pointEarningPolicyService.deactivatePointEarningPolicy(policyId);
	}

	@Test
	void testGetPointTransactions() {
		Pageable pageable = PageRequest.of(0, 10);
		List<GetPointTransactionResponse> transactions = List.of(
			new GetPointTransactionResponse("Policy Name", BigDecimal.valueOf(10), LocalDateTime.now())
		);
		Page<GetPointTransactionResponse> response = new PageImpl<>(transactions, pageable, transactions.size());

		when(pointServiceClient.getPointTransactions(any(Pageable.class))).thenReturn(ResponseEntity.ok(response));

		Page<GetPointTransactionResponse> result = pointEarningPolicyService.getPointTransactions(pageable);

		assertEquals(response, result);
	}
}
