package com.nhnacademy.bookstorefront.order.service;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.feginclient.OrderServiceClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {
	private final OrderServiceClient orderServiceClient;

	public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
		return orderServiceClient.createOrder(createOrderRequest).getBody();
	}


	public GetAllListOrderResponse findAllByCartId(Long cartId) {
		return orderServiceClient.findAllByCartId(cartId).getBody();
	}

	public GetOrderByInfoResponse findByOrderInfoId(String orderInfoId) {
		return orderServiceClient.findByOrderInfoId(orderInfoId).getBody();
	}
}
