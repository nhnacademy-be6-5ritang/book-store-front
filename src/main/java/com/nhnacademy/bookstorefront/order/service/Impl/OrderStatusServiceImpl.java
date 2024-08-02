package com.nhnacademy.bookstorefront.order.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderStatusRequest;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderStatusResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.OrderStatusService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderStatusServiceImpl implements OrderStatusService {
	private final OrderServiceClient orderServiceClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetOrderStatusResponse create(CreateOrderStatusRequest createOrderStatusRequest) {
		return orderServiceClient.createOrderStatus(createOrderStatusRequest).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetOrderStatusResponse update(CreateOrderStatusRequest createOrderStatusRequest, Long id) {
		return orderServiceClient.updateOrderStatus(id, createOrderStatusRequest).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void delete(Long id) {
		orderServiceClient.deleteOrderStatus(id);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public List<GetOrderStatusResponse> findAll() {
		return orderServiceClient.orderStatusAll();
	}
}
