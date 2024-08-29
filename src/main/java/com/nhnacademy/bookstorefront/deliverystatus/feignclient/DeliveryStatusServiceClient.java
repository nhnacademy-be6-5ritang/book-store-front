package com.nhnacademy.bookstorefront.deliverystatus.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.deliverystatus.dto.request.CreateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.request.UpdateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.GetDeliveryStatusResponse;

@FeignClient(name = "delivery-status-feign-client", url = "http://localhost:8090/api/delivery-statuses")
public interface DeliveryStatusServiceClient {

	@GetMapping
	ResponseEntity<List<GetDeliveryStatusResponse>> getDeliveryStatuses();

	@GetMapping("/{deliveryStatusId}")
	ResponseEntity<GetDeliveryStatusResponse> getDeliveryStatus(@PathVariable Long deliveryStatusId);

	@PostMapping
	ResponseEntity<Void> createDeliveryStatus(
		@RequestBody CreateDeliveryStatusRequest request);

	@PutMapping("/{deliveryStatusId}")
	ResponseEntity<Void> updateDeliveryStatus(@PathVariable Long deliveryStatusId,
		@RequestBody UpdateDeliveryStatusRequest request);

	@DeleteMapping("/{deliveryStatusId}")
	ResponseEntity<Void> deleteDeliveryStatus(@PathVariable Long deliveryStatusId);
}
