package com.nhnacademy.bookstorefront.order.service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.OrderCheckNonRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateCartOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderByStatusResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetNonOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetUserPointOrderResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.OrderService;
import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
	private final OrderServiceClient orderServiceClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
		return orderServiceClient.createOrder(createOrderRequest).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetAllListOrderResponse findAllUserId() {
		return orderServiceClient.findAllByUserId().getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetOrderByInfoResponse findByOrderInfoId(String orderInfoId) {
		return orderServiceClient.findByOrderInfoId(orderInfoId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetAllListOrderByStatusResponse findByOrderStatusWait() {
		return orderServiceClient.getOrderStatusWait().getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetAllListOrderByStatusResponse findByOrderStatusGoing() {
		return orderServiceClient.getOrderStatusGoing().getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetAllListOrderByStatusResponse findByOrderStatusComplete() {
		return orderServiceClient.getOrderStatusComplete().getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetAllListOrderByStatusResponse findByOrderStatusRefunded() {
		return orderServiceClient.getOrderStatusRefunded().getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetAllListOrderByStatusResponse findByOrderStatusRefunding() {
		return orderServiceClient.getOrderStatusRefunding().getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetNonOrderByInfoResponse findByOrderInfoIdByEmail(OrderCheckNonRequest orderCheckNonRequest) {
		return orderServiceClient.getOrderByInfoNon(orderCheckNonRequest).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetUserPointOrderResponse getUserPoint() {
		return orderServiceClient.getUserPointOrders().getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void refundedOrder(String orderInfoId) {
		orderServiceClient.refundedOrder(orderInfoId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void refundingOrder(String orderInfoId) {
		orderServiceClient.refundingOrder(orderInfoId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public CreateCartOrderResponse createCartOrder() {
		return orderServiceClient.createCartOrders().getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public CreateOrderResponse updateCartOrder(CreateOrderRequest createOrderRequest, Long orderId) {
		return orderServiceClient.updateCartOrder(createOrderRequest, orderId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetMyUserInfoResponse getMyUserInfoByOrder() {
		return orderServiceClient.getMyUserInfoByInfo().getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<GetAllOrderResponse> findAllPageByUserId(Pageable pageable) {
		return orderServiceClient.findAllPageByUserId(pageable).getBody();
	}
}
