package com.nhnacademy.bookstorefront.deliverystatus.service;

import java.util.List;

import com.nhnacademy.bookstorefront.deliverystatus.dto.request.CreateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.request.UpdateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.CreateDeliveryStatusResponse;
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.GetDeliveryStatusResponse;
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.UpdateDeliveryStatusResponse;

public interface DeliveryStatusService {
	GetDeliveryStatusResponse getDeliveryStatus(Long deliveryStatusId);

	List<GetDeliveryStatusResponse> getDeliveryStatuses();

	CreateDeliveryStatusResponse createDeliveryStatus(CreateDeliveryStatusRequest request);

	UpdateDeliveryStatusResponse updateDeliveryStatus(Long deliveryStatusId, UpdateDeliveryStatusRequest request);

	void deleteDeliveryStatus(Long deliveryStatusId);
}
