package com.nhnacademy.bookstorefront.order.service.Impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.bookstorefront.global.controller.payload.ErrorStatus;
import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderStatusRequest;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderStatusResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.OrderStatusService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderStatusServiceImpl implements OrderStatusService {

	private final OrderServiceClient orderServiceClient;

	@Override
	public GetOrderStatusResponse create(CreateOrderStatusRequest createOrderStatusRequest) {
		return orderServiceClient.createOrderStatus(createOrderStatusRequest).getBody();
	}

	@Override
	public GetOrderStatusResponse update(CreateOrderStatusRequest createOrderStatusRequest, Long id) {
		return orderServiceClient.updateOrderStatus(id, createOrderStatusRequest).getBody();
	}

	@Override
	public void delete(Long id) {
		orderServiceClient.deleteOrderStatus(id);
	}

	@Override
	public List<GetOrderStatusResponse> findAll() {
		return orderServiceClient.orderStatusAll();
	}
}
