package com.nhnacademy.bookstorefront.order.service;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.order.dto.request.CreateBookOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.UpdateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookOrderService {

	private final OrderServiceClient orderServiceClient;

	public CreateBookOrderResponse createBookOrder(CreateBookOrderRequest createBookOrderRequest) {
		return orderServiceClient.createBookOrder(createBookOrderRequest).getBody();
	}

	public UpdateBookOrderResponse updateOrder(Long bookOrderId, Long orderId) {
		return orderServiceClient.updateBookOrder(bookOrderId, orderId).getBody();
	}

	public GetBookOrderResponse getBookOrder(Long bookOrderId) {
		return orderServiceClient.getBookOrder(bookOrderId).getBody();
	}

}
