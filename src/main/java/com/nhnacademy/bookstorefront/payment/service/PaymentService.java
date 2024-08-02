package com.nhnacademy.bookstorefront.payment.service;

import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.CancelResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.GetBookOrderByInfoIdResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.PaymentResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.PaymentSaveResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.TransactionsResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.UpdatePaymentResponse;

/**
 * @author 김다운
 * 결제 관련 서비스의 인터페이스입니다.
 */
public interface PaymentService {
	/**
	 * 결제 생성
	 * @param paymentResponseJson 결제 요청으로 받은 Json 객체
	 * @return 페이먼츠 키 리턴
	 */
	PaymentSaveResponse savePaymentResponse(String paymentResponseJson);

	/**
	 * 결제 조회
	 * @param paymentResponseJson 결제 조회로 받은 Json 객체
	 * @return 결제 정보 리턴
	 */
	TransactionsResponse transactions(String paymentResponseJson);

	/**
	 * 주문 보안 아이디로 주문 리스트 찾기
	 * @param orderInfoId 주문 보안 아이디
	 * @return 주문리스트 리턴
	 */
	GetBookOrderByInfoIdResponse findByOrderInfoId(String orderInfoId);

	/**
	 * 주문 보안 아이디로 주문 찾기
	 * @param orderInfoId 주문 보안 아이디
	 * @return 주문 정보 리턴
	 */
	GetOrderByInfoResponse findByOrder(String orderInfoId);

	/**
	 * 주문 보안 아이디 결제 취소 하기
	 * @param orderInfoId 주문 보안 아이디
	 * @return 결제 페이먼츠 키 , 결제 아이디 리턴
	 */
	CancelResponse paymentFindByOrderInfoId(String orderInfoId);

	/**
	 * 결제 업데이트
	 * @param paymentResponseJson  결제 취소로 받은 Json 객체
	 * @param paymentId 결제 아이디 리턴
	 * @return 결제 취소된 결제 정보 리턴
	 */
	UpdatePaymentResponse updatePayment(String paymentResponseJson, Long paymentId);

	/**
	 * 포인트 결제를 처리합니다.
	 *
	 * @param orderInfoId 주문 정보 ID입 니다. 포인트 결제를 위한 요청에 사용됩니다.
	 */
	void savePointSalePayment(String orderInfoId);

	/**
	 * 포인트 결제를 취소합니다.
	 *
	 * @param paymentId 결제 ID입 니다. 취소할 결제를 식별하는 데 사용됩니다.
	 */
	void cancelPointSalePayment(Long paymentId);

	/**
	 * 주문 정보 ID를 기반으로 결제 정보를 조회합니다.
	 *
	 * @param orderInfoId 주문 정보 ID 입니다. 결제 정보를 조회하는 데 사용됩니다.
	 * @return {@link PaymentResponse} 결제 정보를 담고 있는 DTO 객체입니다.
	 */
	PaymentResponse getPayment(String orderInfoId);
}
