package com.nhnacademy.bookstorefront.order.service;

import com.nhnacademy.bookstorefront.order.dto.response.GetAllPaperResponse;

public interface PaperTypeService {

	/**
	 * 모든 포장지 가져오기
	 * 페이징 처리 예정
	 * @return 모든 포장지 리턴
	 */
	GetAllPaperResponse getAllPaperTypes();

}
