package com.nhnacademy.bookstorefront.order.service;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.order.dto.response.GetListWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetWrappingResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WrappingPaperService {
	private final OrderServiceClient orderServiceClient;
	public GetWrappingResponse createWrappingPapers(Long paperId, Long bookOrderId, Integer quantity) {
		return orderServiceClient.createWrappingPapers(paperId, bookOrderId, quantity).getBody();
	}

	public GetListWrappingResponse getWrappingPaperByOrderListId(Long id) {
		return orderServiceClient.getWrappingPaperByOrderListId(id).getBody();
	}
}
