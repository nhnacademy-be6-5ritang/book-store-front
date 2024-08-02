package com.nhnacademy.bookstorefront.order.service;

import java.util.List;

import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderStatusRequest;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderStatusResponse;

/**
 * @author 김다운
 * 주문상태 관련 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 */
public interface OrderStatusService {
	/**
	 * 주문 상태 만들기
	 * @param createOrderStatusRequest 주문 상태 이름
	 * @return 주문 상태 이름 리턴
	 */
	GetOrderStatusResponse create(CreateOrderStatusRequest createOrderStatusRequest);

	/**
	 * 주문 상태 업데이트
	 * @param createOrderStatusRequest 주문 상태 이름
	 * @param id 주문 상태 아이디
	 * @return 주문 상태 이름
	 */
	GetOrderStatusResponse update(CreateOrderStatusRequest createOrderStatusRequest, Long id);

	/**
	 * 주문 상태 삭제
	 * @param id 주문 상태 아이디
	 */
	void delete(Long id);

	/**
	 * 모든 주문 상태 가져오기
	 * @return 모든 주문 상태
	 */
	List<GetOrderStatusResponse> findAll();
}
