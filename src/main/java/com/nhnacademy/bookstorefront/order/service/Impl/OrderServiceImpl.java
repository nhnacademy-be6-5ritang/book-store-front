package com.nhnacademy.bookstorefront.order.service.Impl;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderByStatusResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.OrderService;

import ch.qos.logback.core.status.ErrorStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
	private final OrderServiceClient orderServiceClient;

	@Override
	public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
		return orderServiceClient.createOrder(createOrderRequest).getBody();
	}

	@Override
	public GetAllListOrderResponse findAllUserId() {
		return orderServiceClient.findAllByUserId().getBody();
	}

	@Override
	public GetOrderByInfoResponse findByOrderInfoId(String orderInfoId) {
		return orderServiceClient.findByOrderInfoId(orderInfoId).getBody();
	}

	@Override
	public GetAllListOrderByStatusResponse findByOrderStatusWait() {
		return orderServiceClient.getOrderStatusWait().getBody();
	}

	@Override
	public GetAllListOrderByStatusResponse findByOrderStatusGoing() {
		return orderServiceClient.getOrderStatusGoing().getBody();
	}
}
