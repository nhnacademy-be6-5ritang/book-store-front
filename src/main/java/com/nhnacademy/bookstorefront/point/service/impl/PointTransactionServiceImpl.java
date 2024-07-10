package com.nhnacademy.bookstorefront.point.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;


import com.nhnacademy.bookstorefront.point.dto.response.GetPointTransactionResponse;
import com.nhnacademy.bookstorefront.point.feignclient.PointServiceClient;
import com.nhnacademy.bookstorefront.point.service.PointTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointTransactionServiceImpl implements PointTransactionService {

	private final PointServiceClient pointServiceClient;

	@Override
	public Page<GetPointTransactionResponse> getPointTransactions(Pageable pageable) {
		return pointServiceClient.getPointTransactions(pageable).getBody();
	}

}
