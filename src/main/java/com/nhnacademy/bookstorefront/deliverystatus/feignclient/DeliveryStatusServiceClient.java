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
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.CreateDeliveryStatusResponse;
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.GetDeliveryStatusResponse;
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.UpdateDeliveryStatusResponse;

@FeignClient(name = "delivery-status-feign-client", url = "http://localhost:8090/back")
public interface DeliveryStatusServiceClient {

	@GetMapping("/deliveries-statuses")
	ResponseEntity<List<GetDeliveryStatusResponse>> getDeliveryStatuses();

	@GetMapping("/deliveries-statuses/{deliveryStatusId}")
	ResponseEntity<GetDeliveryStatusResponse> getDeliveryStatus(@PathVariable Long deliveryStatusId);

	@PostMapping("/deliveries-statuses")
	ResponseEntity<CreateDeliveryStatusResponse> createDeliveryStatus(
		@RequestBody CreateDeliveryStatusRequest request);

	@PutMapping("/deliveries-statuses/{deliveryStatusId}")
	ResponseEntity<UpdateDeliveryStatusResponse> updateDeliveryStatus(@PathVariable Long deliveryStatusId,
		@RequestBody UpdateDeliveryStatusRequest request);

	@DeleteMapping("/deliveries-statuses/{deliveryStatusId}")
	ResponseEntity<Void> deleteDeliveryStatus(@PathVariable Long deliveryStatusId);
}
