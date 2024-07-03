package com.nhnacademy.bookstorefront.order.service;

import com.nhnacademy.bookstorefront.order.dto.response.GetListWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetWrappingResponse;

public interface WrappingPaperService {
	GetWrappingResponse createWrappingPapers(Long paperId, Long bookOrderId, Integer quantity);

	GetListWrappingResponse getWrappingPaperByOrderListId(Long id);
}
