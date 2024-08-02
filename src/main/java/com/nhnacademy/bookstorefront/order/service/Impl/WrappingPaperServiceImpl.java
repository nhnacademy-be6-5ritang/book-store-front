package com.nhnacademy.bookstorefront.order.service.Impl;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.order.dto.response.GetListWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetWrappingResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.WrappingPaperService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WrappingPaperServiceImpl implements WrappingPaperService {
	private final OrderServiceClient orderServiceClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetWrappingResponse createWrappingPapers(Long paperId, Long bookOrderId, Integer quantity) {
		return orderServiceClient.createWrappingPapers(paperId, bookOrderId, quantity).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetListWrappingResponse getWrappingPaperByOrderListId(Long id) {
		return orderServiceClient.getWrappingPaperByOrderListId(id).getBody();
	}
}
