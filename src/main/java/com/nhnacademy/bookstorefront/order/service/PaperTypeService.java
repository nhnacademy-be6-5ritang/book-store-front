package com.nhnacademy.bookstorefront.order.service;

import org.springframework.stereotype.Service;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllPaperResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaperTypeService {
	private final OrderServiceClient orderServiceClient;

	public GetAllPaperResponse getAllPaperTypes() {
		return orderServiceClient.getAllWrappingPapers().getBody();
	}

}
