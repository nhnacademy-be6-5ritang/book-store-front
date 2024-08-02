package com.nhnacademy.bookstorefront.delivery.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.delivery.dto.request.CreateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryByOrderIdRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.response.CreateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.GetDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.UpdateDeliveryAddOrderPolicyResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.UpdateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.feignclient.DeliveryServiceClient;
import com.nhnacademy.bookstorefront.delivery.service.DeliveryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
	private final DeliveryServiceClient deliveryServiceClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<GetDeliveryResponse> getDeliveriesByUserId(Pageable pageable) {
		return deliveryServiceClient.getDeliveriesByUserId(pageable).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetDeliveryResponse getDelivery(Long deliveryId) {
		return deliveryServiceClient.getDelivery(deliveryId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public CreateDeliveryResponse createDelivery(CreateDeliveryRequest request) {
		return deliveryServiceClient.createDelivery(request).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public UpdateDeliveryResponse updateDelivery(Long deliveryId, UpdateDeliveryRequest request) {
		return deliveryServiceClient.updateDelivery(deliveryId, request).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void deleteDelivery(Long deliveryId) {
		deliveryServiceClient.deleteDelivery(deliveryId);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public UpdateDeliveryAddOrderPolicyResponse updateDeliveryAddOrder(Long deliveryId, Long orderId) {
		return deliveryServiceClient.addOrder(deliveryId, orderId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetDeliveryResponse getDeliveryByOrderId(Long orderId) {
		return deliveryServiceClient.getDeliveryByOrder(orderId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void updateDeliveryByOrderId(Long orderId, UpdateDeliveryByOrderIdRequest request) {
		deliveryServiceClient.updateDeliveryByOrderId(orderId, request);
	}
}
