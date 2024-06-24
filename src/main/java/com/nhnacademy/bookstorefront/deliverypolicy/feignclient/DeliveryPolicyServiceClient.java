package com.nhnacademy.bookstorefront.deliverypolicy.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.CreateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.UpdateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.CreateDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPoliciesResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.UpdateDeliveryPolicyResponse;

@FeignClient(name = "book-store-back", url = "http://localhost:8080")
public interface DeliveryPolicyServiceClient {

	@GetMapping("/deliveries-policies")
	ResponseEntity<List<GetDeliveryPoliciesResponse>> getDeliveryPolicies();

	@GetMapping("/deliveries-policies/{deliveryPolicyId}")
	ResponseEntity<GetDeliveryPolicyResponse> getDeliveryPolicy(@PathVariable Long deliveryPolicyId);

	@PostMapping("/deliveries-policies")
	ResponseEntity<CreateDeliveryPolicyResponse> createDeliveryPolicy(
		@RequestBody CreateDeliveryPolicyRequest request);

	@PutMapping("/deliveries-policies/{deliveryPolicyId}")
	ResponseEntity<UpdateDeliveryPolicyResponse> updateDeliveryPolicy(@PathVariable Long deliveryPolicyId,
		@RequestBody UpdateDeliveryPolicyRequest request);

	@DeleteMapping("/deliveries-policies/{deliveryPolicyId}")
	ResponseEntity<Void> deleteDeliveryPolicy(@PathVariable Long deliveryPolicyId);
}
