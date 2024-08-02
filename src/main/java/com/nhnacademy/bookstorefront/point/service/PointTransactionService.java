package com.nhnacademy.bookstorefront.point.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.point.dto.response.GetAllPointTransactionResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointTransactionResponse;

/**
 * @author 김태환
 * 포인트 거래 관련 서비스의 인터페이스입니다.
 */
public interface PointTransactionService {

	/**
	 * 특정 사용자의 포인트 거래 내역을 페이징하여 조회합니다.
	 *
	 * @param pageable 페이징 정보
	 * @return 특정 사용자의 포인트 거래 내역의 페이징된 응답 객체
	 */
	Page<GetPointTransactionResponse> getPointTransactions(Pageable pageable);

	/**
	 * 모든 사용자의 포인트 거래 내역을 페이징하여 조회합니다.
	 *
	 * @param pageable 페이징 정보
	 * @return 모든 사용자의 포인트 거래 내역의 페이징된 응답 객체
	 */
	Page<GetAllPointTransactionResponse> getAllPointTransactions(Pageable pageable);
}
