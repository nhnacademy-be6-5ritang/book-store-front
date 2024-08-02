package com.nhnacademy.bookstorefront.point.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.point.dto.request.CreatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.request.UpdatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.response.CreatePointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointTransactionResponse;
import com.nhnacademy.bookstorefront.point.dto.response.UpdatePointEarningPolicyResponse;

/**
 * @author 김태환
 * 포인트 적립 정책 관련 서비스의 인터페이스입니다.
 */
public interface PointEarningPolicyService {

	/**
	 * 포인트 적립 정책을 생성합니다.
	 *
	 * @param createPointEarningPolicyRequest 포인트 적립 정책 생성 요청 객체
	 * @return 생성된 포인트 적립 정책의 응답 객체
	 */
	CreatePointEarningPolicyResponse createPointEarningPolicy(
		CreatePointEarningPolicyRequest createPointEarningPolicyRequest
	);

	/**
	 * 모든 포인트 적립 정책을 조회합니다.
	 *
	 * @return 포인트 적립 정책의 응답 객체 목록
	 */
	List<GetPointEarningPolicyResponse> getPointEarningPolicies();

	/**
	 * 포인트 적립 정책을 수정합니다.
	 *
	 * @param pointEarningPolicyId 수정할 포인트 적립 정책의 ID
	 * @param updatePointEarningPolicyRequest 포인트 적립 정책 수정 요청 객체
	 * @return 수정된 포인트 적립 정책의 응답 객체
	 */
	UpdatePointEarningPolicyResponse updatePointEarningPolicy(
		Long pointEarningPolicyId, UpdatePointEarningPolicyRequest updatePointEarningPolicyRequest
	);

	/**
	 * 포인트 적립 정책을 활성화합니다.
	 *
	 * @param pointEarningPolicyId 활성화할 포인트 적립 정책의 ID
	 */
	void activatePointEarningPolicy(Long pointEarningPolicyId);

	/**
	 * 포인트 적립 정책을 비활성화합니다.
	 *
	 * @param pointEarningPolicyId 비활성화할 포인트 적립 정책의 ID
	 */
	void deactivatePointEarningPolicy(Long pointEarningPolicyId);

	/**
	 * 포인트 거래 내역을 페이징하여 조회합니다.
	 *
	 * @param pageable 페이징 정보
	 * @return 포인트 거래 내역의 페이징된 응답 객체
	 */
	Page<GetPointTransactionResponse> getPointTransactions(Pageable pageable);
}