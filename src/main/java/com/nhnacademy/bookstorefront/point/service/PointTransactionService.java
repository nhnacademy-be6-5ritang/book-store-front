package com.nhnacademy.bookstorefront.point.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.point.dto.response.GetPointTransactionResponse;

public interface PointTransactionService {
	Page<GetPointTransactionResponse> getPointTransactions(Pageable pageable);

}
