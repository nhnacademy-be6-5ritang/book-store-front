package com.nhnacademy.bookstorefront.order.service.Impl;

import org.springframework.stereotype.Service;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllPaperResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.PaperTypeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaperTypeServiceImpl implements PaperTypeService {
	private final OrderServiceClient orderServiceClient;

	@Override
	public GetAllPaperResponse getAllPaperTypes() {
		return orderServiceClient.getAllWrappingPapers().getBody();
	}

}
