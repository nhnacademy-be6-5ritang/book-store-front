package com.nhnacademy.bookstorefront.deliverypolicy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.CreateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.UpdateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPoliciesResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.feignclient.DeliveryPolicyServiceClient;
import com.nhnacademy.bookstorefront.deliverypolicy.service.impl.DeliveryPolicyServiceImpl;

class DeliveryPolicyServiceImplTest {

	private DeliveryPolicyService deliveryPolicyService;

	@Mock
	private DeliveryPolicyServiceClient deliveryPolicyServiceClient;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		deliveryPolicyService = new DeliveryPolicyServiceImpl(deliveryPolicyServiceClient);
	}

	@Test
	void testGetDeliveryPolicies() {
		GetDeliveryPoliciesResponse response = new GetDeliveryPoliciesResponse(1L, "Free Shipping");
		List<GetDeliveryPoliciesResponse> responseList = List.of(response);
		when(deliveryPolicyServiceClient.getDeliveryPolicies()).thenReturn(ResponseEntity.ok(responseList));

		List<GetDeliveryPoliciesResponse> result = deliveryPolicyService.getDeliveryPolicies();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(response, result.get(0));
		verify(deliveryPolicyServiceClient, times(1)).getDeliveryPolicies();
	}

	@Test
	void testGetDeliveryPolicy() {
		GetDeliveryPolicyResponse response = new GetDeliveryPolicyResponse(
			1L, "Free Shipping", BigDecimal.valueOf(0), "Free shipping on orders over $50", BigDecimal.valueOf(50)
		);
		when(deliveryPolicyServiceClient.getDeliveryPolicy(anyLong())).thenReturn(ResponseEntity.ok(response));

		GetDeliveryPolicyResponse result = deliveryPolicyService.getDeliveryPolicy(1L);

		assertNotNull(result);
		assertEquals(response, result);
		verify(deliveryPolicyServiceClient, times(1)).getDeliveryPolicy(1L);
	}

	@Test
	void testCreateDeliveryPolicy() {
		CreateDeliveryPolicyRequest request = new CreateDeliveryPolicyRequest(
			"New Policy", "Description of new policy", BigDecimal.valueOf(100), BigDecimal.valueOf(200)
		);

		deliveryPolicyService.createDeliveryPolicy(request);

		verify(deliveryPolicyServiceClient, times(1)).createDeliveryPolicy(request);
	}

	@Test
	void testUpdateDeliveryPolicy() {
		UpdateDeliveryPolicyRequest request = new UpdateDeliveryPolicyRequest(
			"Updated Policy", "Updated description", BigDecimal.valueOf(150), BigDecimal.valueOf(250)
		);

		deliveryPolicyService.updateDeliveryPolicy(1L, request);

		verify(deliveryPolicyServiceClient, times(1)).updateDeliveryPolicy(1L, request);
	}

	@Test
	void testDeleteDeliveryPolicy() {
		deliveryPolicyService.deleteDeliveryPolicy(1L);

		verify(deliveryPolicyServiceClient, times(1)).deleteDeliveryPolicy(1L);
	}

	@Test
	void testFindByDeliveryPolicyStandardPriceLessThanEqualOrderByDeliveryPolicyStandardPriceDesc() {
		GetDeliveryPolicyResponse response = new GetDeliveryPolicyResponse(
			1L, "Free Shipping", BigDecimal.valueOf(0), "Free shipping on orders over $50", BigDecimal.valueOf(50)
		);
		when(deliveryPolicyServiceClient.addPolicy(anyLong(), any(BigDecimal.class)))
			.thenReturn(ResponseEntity.ok(response));

		GetDeliveryPolicyResponse result = deliveryPolicyService.findByDeliveryPolicyStandardPriceLessThanEqualOrderByDeliveryPolicyStandardPriceDesc(
			1L, BigDecimal.valueOf(50));

		assertNotNull(result);
		assertEquals(response, result);
		verify(deliveryPolicyServiceClient, times(1)).addPolicy(1L, BigDecimal.valueOf(50));
	}
}
