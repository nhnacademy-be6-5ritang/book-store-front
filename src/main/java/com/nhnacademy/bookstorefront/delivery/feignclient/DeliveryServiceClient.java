package com.nhnacademy.bookstorefront.delivery.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.delivery.dto.request.CreateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.GetDeliveriesRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.response.CreateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.GetDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.UpdateDeliveryResponse;

@FeignClient(name = "delivery-feign-client", url = "http://localhost:8083")
public interface DeliveryServiceClient {

	@GetMapping
	ResponseEntity<Page<GetDeliveryResponse>> getDeliveriesByUserId(@RequestParam("page") int page,
		@RequestParam("size") int size,
		@RequestParam(required = false) String sort, @RequestBody GetDeliveriesRequest request);

	@GetMapping("/{deliveryId}")
	ResponseEntity<GetDeliveryResponse> getDelivery(@PathVariable Long deliveryId);

	@PostMapping
	ResponseEntity<CreateDeliveryResponse> createDelivery(@RequestBody CreateDeliveryRequest request);

	@PutMapping("/{deliveryId}")
	ResponseEntity<UpdateDeliveryResponse> updateDelivery(@PathVariable Long deliveryId,
		@RequestBody UpdateDeliveryRequest request);

	@DeleteMapping("/{deliveryId}")
	ResponseEntity<Void> deleteDelivery(@PathVariable Long deliveryId);

}
