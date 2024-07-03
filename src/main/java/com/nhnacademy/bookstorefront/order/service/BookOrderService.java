package com.nhnacademy.bookstorefront.order.service;

import com.nhnacademy.bookstorefront.order.dto.request.CreateBookOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.UpdateBookOrderResponse;

public interface BookOrderService {
	CreateBookOrderResponse createBookOrder(CreateBookOrderRequest createBookOrderRequest);

	UpdateBookOrderResponse updateOrder(Long bookOrderId, Long orderId);

	GetBookOrderResponse getBookOrder(Long bookOrderId);
}
