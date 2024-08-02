package com.nhnacademy.bookstorefront.order.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.order.dto.request.CreateBookOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.UpdateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.BookOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookOrderServiceImpl implements BookOrderService {
	private final OrderServiceClient orderServiceClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public CreateBookOrderResponse createBookOrder(CreateBookOrderRequest createBookOrderRequest) {
		return orderServiceClient.createBookOrder(createBookOrderRequest).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public UpdateBookOrderResponse updateOrder(Long bookOrderId, Long orderId) {
		return orderServiceClient.updateBookOrder(bookOrderId, orderId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetBookOrderResponse getBookOrder(Long bookOrderId) {
		return orderServiceClient.getBookOrder(bookOrderId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public List<GetBookOrderResponse> getBookOrderByOrderId(String orderInfoId) {
		return orderServiceClient.getCartOrder(orderInfoId).getBody();
	}
}
