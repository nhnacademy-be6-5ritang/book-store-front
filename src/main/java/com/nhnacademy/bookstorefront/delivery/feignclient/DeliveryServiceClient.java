package com.nhnacademy.bookstorefront.delivery.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.delivery.dto.request.CreateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryByOrderIdRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.response.CreateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.GetDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.UpdateDeliveryAddOrderPolicyResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.UpdateDeliveryResponse;

@FeignClient(name = "delivery-feign-client", url = "http://localhost:8090/api/deliveries")
public interface DeliveryServiceClient {

	@GetMapping("/me/page")
	ResponseEntity<Page<GetDeliveryResponse>> getDeliveriesByUserId(Pageable pageable);

	@GetMapping("/{deliveryId}")
	ResponseEntity<GetDeliveryResponse> getDelivery(@PathVariable Long deliveryId);

	@PostMapping
	ResponseEntity<CreateDeliveryResponse> createDelivery(@RequestBody CreateDeliveryRequest request);

	@PutMapping("/{deliveryId}")
	ResponseEntity<UpdateDeliveryResponse> updateDelivery(@PathVariable Long deliveryId,
		@RequestBody UpdateDeliveryRequest request);

	@DeleteMapping("/{deliveryId}")
	ResponseEntity<Void> deleteDelivery(@PathVariable Long deliveryId);

	@PutMapping("/{deliveryId}/{orderId}/orders")
	ResponseEntity<UpdateDeliveryAddOrderPolicyResponse> addOrder(@PathVariable Long deliveryId,
		@PathVariable Long orderId);

	@GetMapping("/{orderId}/orders")
	ResponseEntity<GetDeliveryResponse> getDeliveryByOrder(@PathVariable Long orderId);

	@PutMapping("/sender/{orderId}")
	ResponseEntity<Void> updateDeliveryByOrderId(@PathVariable Long orderId, @RequestBody
	UpdateDeliveryByOrderIdRequest request);
}
