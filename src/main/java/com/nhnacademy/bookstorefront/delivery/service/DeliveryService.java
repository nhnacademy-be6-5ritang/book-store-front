package com.nhnacademy.bookstorefront.delivery.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.delivery.dto.request.CreateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryByOrderIdRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.response.CreateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.GetDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.UpdateDeliveryAddOrderPolicyResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.UpdateDeliveryResponse;

public interface DeliveryService {
	Page<GetDeliveryResponse> getDeliveriesByUserId(Pageable pageable);

	GetDeliveryResponse getDelivery(Long deliveryId);

	CreateDeliveryResponse createDelivery(CreateDeliveryRequest request);

	UpdateDeliveryResponse updateDelivery(Long deliveryId, UpdateDeliveryRequest request);

	void deleteDelivery(Long deliveryId);

	UpdateDeliveryAddOrderPolicyResponse updateDeliveryAddOrder(Long deliveryId, Long orderId);

	GetDeliveryResponse getDeliveryByOrderId(Long orderId);

	void updateDeliveryByOrderId(Long orderId, UpdateDeliveryByOrderIdRequest request);

}
