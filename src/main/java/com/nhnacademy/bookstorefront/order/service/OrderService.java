package com.nhnacademy.bookstorefront.order.service;

import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;

public interface OrderService {
	CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest);

	GetAllListOrderResponse findAllByCartId(Long cartId);

	GetOrderByInfoResponse findByOrderInfoId(String orderInfoId);
}
