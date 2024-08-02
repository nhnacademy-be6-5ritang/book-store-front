package com.nhnacademy.bookstorefront.order.service;

import com.nhnacademy.bookstorefront.order.dto.request.CreateWrappingTypeRequest;
import com.nhnacademy.bookstorefront.order.dto.request.UpdateWrappingTypeRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreatePaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAdminAllPaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllPaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetPaperResponse;

/**
 * @author 김다운
 * 포장지 유형 관련 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 */
public interface PaperTypeService {

	/**
	 * 모든 포장지 가져오기
	 * 페이징 처리 예정
	 * @return 모든 포장지 리턴
	 */
	GetAllPaperResponse getAllPaperTypes();

	/**
	 * 포장지 업데이트
	 * @param id 포장지 아이디
	 * @param updateWrappingTypeRequest 포장지 정보
	 * @return 포장지 정보
	 */
	GetPaperResponse updatePaperTypeById(Long id, UpdateWrappingTypeRequest updateWrappingTypeRequest);

	/**
	 * 포장지 삭제
	 * @param id 포장지 아이디
	 */
	void deletePaperTypeById(Long id);

	/**
	 * 포장지 만들기
	 * @param createWrappingTypeRequest 포장지 정보
	 * @return 포장지 데이터 전부
	 */
	CreatePaperResponse createPaper(CreateWrappingTypeRequest createWrappingTypeRequest);

	/**
	 * 관리자용 포장지 전부 가져오기
	 * @return 포장지 전부
	 */
	GetAdminAllPaperResponse getAdminAllPaperTypes();

}
