package com.nhnacademy.bookstorefront.delivery.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.delivery.dto.request.CreateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryByOrderIdRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.response.CreateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.GetDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.UpdateDeliveryAddOrderPolicyResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.UpdateDeliveryResponse;

/**
 * @author 이경헌, 김다운
 * DeliveryService 는 배송 관련 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 */
public interface DeliveryService {

	/**
	 * 사용자의 배송 목록을 페이지네이션하여 조회합니다.
	 *
	 * @param pageable 페이지 정보 (페이지 번호와 페이지 크기)
	 * @return 사용자의 배송 목록을 포함하는 페이지 객체
	 */
	Page<GetDeliveryResponse> getDeliveriesByUserId(Pageable pageable);

	/**
	 * 주어진 배송 ID에 해당하는 배송의 상세 정보를 조회합니다.
	 *
	 * @param deliveryId 배송 ID
	 * @return 배송 상세 정보 DTO
	 */
	GetDeliveryResponse getDelivery(Long deliveryId);

	/**
	 * 새로운 배송을 생성합니다.
	 *
	 * @param request 생성할 배송 정보 DTO
	 * @return 생성된 배송의 정보 DTO
	 */
	CreateDeliveryResponse createDelivery(CreateDeliveryRequest request);

	/**
	 * 주어진 배송 ID에 해당하는 배송을 업데이트합니다.
	 *
	 * @param deliveryId 업데이트할 배송 ID
	 * @param request    업데이트할 배송 정보 DTO
	 * @return 업데이트된 배송의 정보 DTO
	 */
	UpdateDeliveryResponse updateDelivery(Long deliveryId, UpdateDeliveryRequest request);

	/**
	 * 주어진 배송 ID에 해당하는 배송을 삭제합니다.
	 *
	 * @param deliveryId 삭제할 배송 ID
	 */
	void deleteDelivery(Long deliveryId);

	/**
	 * 주어진 배송 ID에 배송 정책을 추가합니다.
	 *
	 * @param deliveryId 배송 ID
	 * @param orderId    주문 ID
	 * @return 업데이트된 배송 정책의 정보 DTO
	 */
	UpdateDeliveryAddOrderPolicyResponse updateDeliveryAddOrder(Long deliveryId, Long orderId);

	/**
	 * 주어진 주문 ID에 해당하는 배송을 조회합니다.
	 *
	 * @param orderId 주문 ID
	 * @return 해당 주문의 배송 정보 DTO
	 */
	GetDeliveryResponse getDeliveryByOrderId(Long orderId);

	/**
	 * 주어진 주문 ID에 대해 배송 정보를 업데이트합니다.
	 *
	 * @param orderId 주문 ID
	 * @param request 업데이트할 배송 정보 DTO
	 */
	void updateDeliveryByOrderId(Long orderId, UpdateDeliveryByOrderIdRequest request);
}
