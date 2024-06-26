package com.nhnacademy.bookstorefront.deliverystatus.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.deliverystatus.dto.request.CreateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.request.UpdateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.CreateDeliveryStatusResponse;
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.GetDeliveryStatusResponse;
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.UpdateDeliveryStatusResponse;
import com.nhnacademy.bookstorefront.deliverystatus.feignclient.DeliveryStatusServiceClient;
import com.nhnacademy.bookstorefront.deliverystatus.service.DeliveryStatusService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryStatusServiceImpl implements DeliveryStatusService {
	private final DeliveryStatusServiceClient deliveryStatusServiceClient;

	@Override
	public GetDeliveryStatusResponse getDeliveryStatus(Long deliveryStatusId) {
		return deliveryStatusServiceClient.getDeliveryStatus(deliveryStatusId).getBody();
	}

	@Override
	public List<GetDeliveryStatusResponse> getDeliveryStatuses() {
		return deliveryStatusServiceClient.getDeliveryStatuses().getBody();
	}

	@Override
	public CreateDeliveryStatusResponse createDeliveryStatus(CreateDeliveryStatusRequest request) {
		return deliveryStatusServiceClient.createDeliveryStatus(request).getBody();
	}

	@Override
	public UpdateDeliveryStatusResponse updateDeliveryStatus(Long deliveryStatusId,
		UpdateDeliveryStatusRequest request) {
		return deliveryStatusServiceClient.updateDeliveryStatus(deliveryStatusId, request).getBody();
	}

	@Override
	public void deleteDeliveryStatus(Long deliveryStatusId) {
		deliveryStatusServiceClient.deleteDeliveryStatus(deliveryStatusId);
	}
}
