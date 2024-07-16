package com.nhnacademy.bookstorefront.deliverypolicy.service;

import java.util.List;

import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.CreateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.UpdateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPoliciesResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;

/**
 * @author 이경헌
 * 배송 정책 관련 서비스 인터페이스입니다.
 */
public interface DeliveryPolicyService {
	/**
	 * 모든 배송 정책을 조회합니다.
	 *
	 * @return 모든 배송 정책 목록
	 */
	List<GetDeliveryPoliciesResponse> getDeliveryPolicies();

	/**
	 * 주어진 배송 정책 ID에 해당하는 배송 정책을 조회합니다.
	 *
	 * @param deliveryPolicyId 조회할 배송 정책의 ID
	 * @return 조회된 배송 정책 정보
	 */
	GetDeliveryPolicyResponse getDeliveryPolicy(Long deliveryPolicyId);

	/**
	 * 새로운 배송 정책을 생성합니다.
	 *
	 * @param request 생성할 배송 정책 정보를 담은 요청 객체
	 */
	void createDeliveryPolicy(CreateDeliveryPolicyRequest request);

	/**
	 * 주어진 배송 정책 ID에 해당하는 배송 정책을 수정합니다.
	 *
	 * @param deliveryPolicyId 수정할 배송 정책의 ID
	 * @param request          수정할 배송 정책 정보를 담은 요청 객체
	 */
	void updateDeliveryPolicy(Long deliveryPolicyId, UpdateDeliveryPolicyRequest request);

	/**
	 * 주어진 배송 정책 ID에 해당하는 배송 정책을 삭제합니다.
	 *
	 * @param deliveryPolicyId 삭제할 배송 정책의 ID
	 */
	void deleteDeliveryPolicy(Long deliveryPolicyId);
}
