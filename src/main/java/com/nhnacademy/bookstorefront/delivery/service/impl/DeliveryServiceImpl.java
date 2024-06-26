package com.nhnacademy.bookstorefront.delivery.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.delivery.dto.request.CreateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.GetDeliveriesRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.response.CreateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.GetDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.UpdateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.feignclient.DeliveryServiceClient;
import com.nhnacademy.bookstorefront.delivery.service.DeliveryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
	private final DeliveryServiceClient deliveryServiceClient;

	@Override
	public Page<GetDeliveryResponse> getDeliveriesByUserId(int page, int size, String sort,
		GetDeliveriesRequest request) {
		return deliveryServiceClient.getDeliveriesByUserId(page, size, sort, request).getBody();
	}

	@Override
	public GetDeliveryResponse getDelivery(Long deliveryId) {
		return deliveryServiceClient.getDelivery(deliveryId).getBody();
	}

	@Override
	public CreateDeliveryResponse createDelivery(CreateDeliveryRequest request) {
		return deliveryServiceClient.createDelivery(request).getBody();
	}

	@Override
	public UpdateDeliveryResponse updateDelivery(Long deliveryId, UpdateDeliveryRequest request) {
		return deliveryServiceClient.updateDelivery(deliveryId, request).getBody();
	}

	@Override
	public void deleteDelivery(Long deliveryId) {
		deliveryServiceClient.deleteDelivery(deliveryId);
	}
}
