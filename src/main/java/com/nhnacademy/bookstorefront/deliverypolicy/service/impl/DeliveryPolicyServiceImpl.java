package com.nhnacademy.bookstorefront.deliverypolicy.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.CreateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.UpdateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.CreateDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPoliciesResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.UpdateDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.feignclient.DeliveryPolicyServiceClient;
import com.nhnacademy.bookstorefront.deliverypolicy.service.DeliveryPolicyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryPolicyServiceImpl implements DeliveryPolicyService {
	private final DeliveryPolicyServiceClient deliveryPolicyServiceClient;

	@Override
	public List<GetDeliveryPoliciesResponse> getDeliveryPolicies() {
		return deliveryPolicyServiceClient.getDeliveryPolicies().getBody();
	}

	@Override
	public GetDeliveryPolicyResponse getDeliveryPolicy(Long deliveryPolicyId) {
		return deliveryPolicyServiceClient.getDeliveryPolicy(deliveryPolicyId).getBody();
	}

	@Override
	public void createDeliveryPolicy(CreateDeliveryPolicyRequest request) {
		deliveryPolicyServiceClient.createDeliveryPolicy(request);
	}

	@Override
	public void updateDeliveryPolicy(Long deliveryPolicyId,
		UpdateDeliveryPolicyRequest request) {
		deliveryPolicyServiceClient.updateDeliveryPolicy(deliveryPolicyId, request);
	}

	@Override
	public void deleteDeliveryPolicy(Long deliveryPolicyId) {
		deliveryPolicyServiceClient.deleteDeliveryPolicy(deliveryPolicyId);
	}
}
