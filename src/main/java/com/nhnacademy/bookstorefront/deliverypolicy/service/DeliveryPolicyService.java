package com.nhnacademy.bookstorefront.deliverypolicy.service;

import java.util.List;

import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.CreateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.UpdateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.CreateDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPoliciesResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.UpdateDeliveryPolicyResponse;

public interface DeliveryPolicyService {
	List<GetDeliveryPoliciesResponse> getDeliveryPolicies();

	GetDeliveryPolicyResponse getDeliveryPolicy(Long deliveryPolicyId);

	void createDeliveryPolicy(CreateDeliveryPolicyRequest request);

	void updateDeliveryPolicy(Long deliveryPolicyId, UpdateDeliveryPolicyRequest request);

	void deleteDeliveryPolicy(Long deliveryPolicyId);
}
