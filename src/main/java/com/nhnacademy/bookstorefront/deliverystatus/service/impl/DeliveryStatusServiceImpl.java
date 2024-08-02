package com.nhnacademy.bookstorefront.deliverystatus.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.deliverystatus.dto.request.CreateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.request.UpdateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.GetDeliveryStatusResponse;
import com.nhnacademy.bookstorefront.deliverystatus.feignclient.DeliveryStatusServiceClient;
import com.nhnacademy.bookstorefront.deliverystatus.service.DeliveryStatusService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryStatusServiceImpl implements DeliveryStatusService {
	private final DeliveryStatusServiceClient deliveryStatusServiceClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetDeliveryStatusResponse getDeliveryStatus(Long deliveryStatusId) {
		return deliveryStatusServiceClient.getDeliveryStatus(deliveryStatusId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public List<GetDeliveryStatusResponse> getDeliveryStatuses() {
		return deliveryStatusServiceClient.getDeliveryStatuses().getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void createDeliveryStatus(CreateDeliveryStatusRequest request) {
		deliveryStatusServiceClient.createDeliveryStatus(request);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void updateDeliveryStatus(Long deliveryStatusId,
		UpdateDeliveryStatusRequest request) {
		deliveryStatusServiceClient.updateDeliveryStatus(deliveryStatusId, request);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void deleteDeliveryStatus(Long deliveryStatusId) {
		deliveryStatusServiceClient.deleteDeliveryStatus(deliveryStatusId);
	}
}
