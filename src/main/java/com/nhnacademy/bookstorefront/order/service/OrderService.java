package com.nhnacademy.bookstorefront.order.service;

import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderByStatusResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;

public interface OrderService {
	/**
	 * 주문 생성
	 * @param createOrderRequest 입력 받은 주문 정보
	 * @return 일부 주문 정보 리턴
	 */
	CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest);

	/**
	 * 카트 아이디로 모든 주문 가져오기
	 * 페이징 처리 예정
	 * @return 일부 주문 정보를 가진 리스트 리턴
	 */
	GetAllListOrderResponse findAllUserId();

	/**
	 * 주문 보안 아이디로 주문 찾기
	 * @param orderInfoId 주문 보안 아이디
	 * @return 일부 주문 정보 리턴
	 */
	GetOrderByInfoResponse findByOrderInfoId(String orderInfoId);

	/**
	 * 주문 상태가 대기인 모든 주문 가져오기
	 * @return 주문 상태가 대기인 주문 리턴
	 */
	GetAllListOrderByStatusResponse findByOrderStatusWait();

	/**
	 * 주문 상태가 발송인 모든 주문 가져오기
	 * @return 주문 리턴
	 */
	GetAllListOrderByStatusResponse findByOrderStatusGoing();
}
