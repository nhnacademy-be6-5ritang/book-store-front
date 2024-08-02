package com.nhnacademy.bookstorefront.deliverypolicy.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.CreateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.UpdateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPoliciesResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.feignclient.DeliveryPolicyServiceClient;
import com.nhnacademy.bookstorefront.deliverypolicy.service.DeliveryPolicyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryPolicyServiceImpl implements DeliveryPolicyService {
	private final DeliveryPolicyServiceClient deliveryPolicyServiceClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public List<GetDeliveryPoliciesResponse> getDeliveryPolicies() {
		return deliveryPolicyServiceClient.getDeliveryPolicies().getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetDeliveryPolicyResponse getDeliveryPolicy(Long deliveryPolicyId) {
		return deliveryPolicyServiceClient.getDeliveryPolicy(deliveryPolicyId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void createDeliveryPolicy(CreateDeliveryPolicyRequest request) {
		deliveryPolicyServiceClient.createDeliveryPolicy(request);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void updateDeliveryPolicy(Long deliveryPolicyId,
		UpdateDeliveryPolicyRequest request) {
		deliveryPolicyServiceClient.updateDeliveryPolicy(deliveryPolicyId, request);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void deleteDeliveryPolicy(Long deliveryPolicyId) {
		deliveryPolicyServiceClient.deleteDeliveryPolicy(deliveryPolicyId);
	}

	/**
	 *{@inheritDoc}
	 */
	public GetDeliveryPolicyResponse findByDeliveryPolicyStandardPriceLessThanEqualOrderByDeliveryPolicyStandardPriceDesc(
		Long deliveryId, BigDecimal price) {
		return deliveryPolicyServiceClient.addPolicy(deliveryId, price).getBody();
	}
}
