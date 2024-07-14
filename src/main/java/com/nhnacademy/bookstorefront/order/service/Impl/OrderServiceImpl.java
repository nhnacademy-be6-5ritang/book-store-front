package com.nhnacademy.bookstorefront.order.service.Impl;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.OrderCheckNonRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderByStatusResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetNonOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetUserPointOrderResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.OrderService;
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

	@Override
	public GetAllListOrderByStatusResponse findByOrderStatusComplete() {
		return orderServiceClient.getOrderStatusComplete().getBody();
	}

	@Override
	public GetAllListOrderByStatusResponse findByOrderStatusRefunded() {
		return orderServiceClient.getOrderStatusRefunded().getBody();
	}

	@Override
	public GetAllListOrderByStatusResponse findByOrderStatusRefunding() {
		return orderServiceClient.getOrderStatusRefunding().getBody();
	}

	@Override
	public GetNonOrderByInfoResponse findByOrderInfoIdByEmail(OrderCheckNonRequest orderCheckNonRequest) {
		return orderServiceClient.getOrderByInfoNon(orderCheckNonRequest).getBody();
	}

	@Override
	public GetUserPointOrderResponse getUserPoint() {
		return orderServiceClient.getUserPointOrders().getBody();
	}

	@Override
	public void refundedOrder(String orderInfoId) {
		orderServiceClient.refundedOrder(orderInfoId).getBody();
	}

	@Override
	public void refundingOrder(String orderInfoId) {
		orderServiceClient.refundingOrder(orderInfoId).getBody();
	}
}
