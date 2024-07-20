package com.nhnacademy.bookstorefront.deliverypolicy.feignclient;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.CreateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.UpdateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPoliciesResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;

@FeignClient(name = "delivery-policy-feign-client", url = "http://localhost:8090/api/deliveryPolicies")
public interface DeliveryPolicyServiceClient {

	@GetMapping
	ResponseEntity<List<GetDeliveryPoliciesResponse>> getDeliveryPolicies();

	@GetMapping("/{deliveryPolicyId}")
	ResponseEntity<GetDeliveryPolicyResponse> getDeliveryPolicy(@PathVariable Long deliveryPolicyId);

	@PostMapping
	ResponseEntity<Void> createDeliveryPolicy(
		@RequestBody CreateDeliveryPolicyRequest request);

	@PutMapping("/{deliveryPolicyId}")
	ResponseEntity<Void> updateDeliveryPolicy(@PathVariable Long deliveryPolicyId,
		@RequestBody UpdateDeliveryPolicyRequest request);

	@DeleteMapping("/{deliveryPolicyId}")
	ResponseEntity<Void> deleteDeliveryPolicy(@PathVariable Long deliveryPolicyId);

	@PutMapping("/{deliveryId}/{price}/addPolicies")
	ResponseEntity<GetDeliveryPolicyResponse> addPolicy(@PathVariable Long deliveryId, @PathVariable BigDecimal price);
}
