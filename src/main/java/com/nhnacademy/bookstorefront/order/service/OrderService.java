package com.nhnacademy.bookstorefront.order.service;

import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.OrderCheckNonRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateCartOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderByStatusResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetNonOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetUserPointOrderResponse;

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

	/**
	 * 주문 상태가 완료인 모든 주문 가져오기
	 * @return 주문 리턴
	 */
	GetAllListOrderByStatusResponse findByOrderStatusComplete();

	/**
	 * 주문 상태가 환불 진행중인 모든 주문 가져오기
	 * @return 주문 리턴
	 */
	GetAllListOrderByStatusResponse findByOrderStatusRefunded();

	/**
	 * 주문 상태가 환불 모든 주문 가져오기
	 * @return 주문 리턴
	 */
	GetAllListOrderByStatusResponse findByOrderStatusRefunding();

	/**
	 * 비회원 주문 확인
	 * @return 조회된 주문 정보
	 */
	GetNonOrderByInfoResponse findByOrderInfoIdByEmail(OrderCheckNonRequest orderCheckNonRequest);

	/**
	 * 회원 포인트 가져오기
	 * @return 회원 보유 포인트
	 */
	GetUserPointOrderResponse getUserPoint();

	/**
	 * 반품
	 * @param orderInfoId 주문 보안 아이디
	 */
	void refundedOrder(String orderInfoId);

	/**
	 * 반품 요청중
	 * @param orderInfoId 주문 보안 아이디
	 */
	void refundingOrder(String orderInfoId);

	/**
	 * 카트 주문 생성
	 * @return 주문 아이디
	 */
	CreateCartOrderResponse createCartOrder();

	/**
	 * 카트 주문 업데이트
	 * @param createOrderRequest 업데이트 내용
	 * @param orderId 주문 아이디
	 * @return 주문 완료 페이지 내용
	 */
	CreateOrderResponse updateCartOrder(CreateOrderRequest createOrderRequest, Long orderId);
}
