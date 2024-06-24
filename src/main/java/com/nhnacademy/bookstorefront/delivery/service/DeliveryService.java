package com.nhnacademy.bookstorefront.delivery.service;

import org.springframework.data.domain.Page;

import com.nhnacademy.bookstorefront.delivery.dto.request.CreateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.GetDeliveriesRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.response.CreateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.GetDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.UpdateDeliveryResponse;

public interface DeliveryService {
	Page<GetDeliveryResponse> getDeliveriesByUserId(int page, int size, String sort, GetDeliveriesRequest request);

	GetDeliveryResponse getDelivery(Long deliveryId);

	CreateDeliveryResponse createDelivery(CreateDeliveryRequest request);

	UpdateDeliveryResponse updateDelivery(Long deliveryId, UpdateDeliveryRequest request);

	void deleteDelivery(Long deliveryId);
}
