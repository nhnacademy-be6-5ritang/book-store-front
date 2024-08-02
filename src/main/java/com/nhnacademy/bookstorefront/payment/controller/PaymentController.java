package com.nhnacademy.bookstorefront.payment.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.keymanager.service.KeyManagerService;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.service.Impl.OrderServiceImpl;
import com.nhnacademy.bookstorefront.payment.dto.request.CancelTextRequest;
import com.nhnacademy.bookstorefront.payment.dto.request.PaymentConfirmationRequest;
import com.nhnacademy.bookstorefront.payment.dto.response.CancelResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.GetBookOrderByInfoIdResponse;
import com.nhnacademy.bookstorefront.payment.service.impl.PaymentServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 김다운
 * 결제 관련 HTTP 요청을 처리하는 컨트롤러입니다.
 */
@Slf4j
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentServiceImpl paymentServiceImpl;
	private final RestTemplate paymentRestTemplate;
	private final OrderServiceImpl orderServiceImpl;
	private final KeyManagerService keyManagerService;

	/**
	 * 주문 결제 전 주문 보안 아이디로 주문을 html에 설정
	 * @param orderInfoId 주문 보안 아이디
	 * @return 토스 페이먼츠 결제창 html 이동
	 */
	@GetMapping("{order_info_id}")
	public ModelAndView payment(@PathVariable("order_info_id") String orderInfoId) {
		GetOrderByInfoResponse orderInfo = paymentServiceImpl.findByOrder(orderInfoId);
		GetBookOrderByInfoIdResponse bookOrder = paymentServiceImpl.findByOrderInfoId(orderInfoId);
		ModelAndView view = new ModelAndView();
		view.addObject("orderName", bookOrder.title());
		view.addObject("orderId", orderInfoId);
		view.addObject("price", orderInfo.price());
		view.addObject("payerName", orderInfo.payername());
		view.setViewName("toss/toss");
		return view;
	}

	/**
	 * 주문 성공 시 결제 승인 요청 및 장바구니 비우기
	 * @param orderId 주문 보안 아이디
	 * @param paymentKey 토스 페이먼츠 키
	 * @param amount 결제 금액
	 * @return 주문 완료 페이지로 이동
	 */
	@GetMapping("/success")
	public ModelAndView paymentSuccess(@RequestParam String orderId, @RequestParam String paymentKey,
		@RequestParam String amount) {

		String apiUrl = "https://api.tosspayments.com/v1/payments/confirm";
		String authToken = keyManagerService.getSecret("176afe7c8c07476cb319873dbb99af00");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", authToken);

		HttpEntity<PaymentConfirmationRequest> entity = new HttpEntity<>(
			PaymentConfirmationRequest.form(paymentKey, Integer.parseInt(amount), orderId), headers);
		String response = paymentRestTemplate.postForObject(apiUrl, entity, String.class);

		paymentServiceImpl.savePaymentResponse(response);
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:/api/orders/complete/" + orderId);
		return view;
	}

	/**
	 * 결제 실패 시 실패 페이지로 이동
	 * 변경 예정
	 * @return 실패 페이지로 이동
	 */
	@GetMapping("/fail")
	public ModelAndView paymentFail() {
		ModelAndView view = new ModelAndView();
		view.setViewName("toss/fail");
		return view;
	}

	/**
	 * 주문 보안 아이디로 결제 조회
	 * @param orderInfoId 주문 보안 아이디
	 * @return 결제 조회 정보 페이지로 이동
	 */
	@GetMapping("/transactions/{order_info_id}")
	public ModelAndView paymentTransactions(@PathVariable("order_info_id") String orderInfoId) {
		GetOrderByInfoResponse order = orderServiceImpl.findByOrderInfoId(orderInfoId);
		if (order.price().equals(new BigDecimal("0.00"))) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("paymentInfo", paymentServiceImpl.getPayment(orderInfoId));
			modelAndView.addObject("orderStatus", paymentServiceImpl.findByOrder(orderInfoId).status());
			modelAndView.setViewName("toss/transactions-public");
			return modelAndView;
		}
		String url = "https://api.tosspayments.com/v1/payments/orders/" + orderInfoId;

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", keyManagerService.getSecret("176afe7c8c07476cb319873dbb99af00"));

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = paymentRestTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		String responseBody = response.getBody();

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("paymentInfo", paymentServiceImpl.transactions(responseBody));
		modelAndView.addObject("orderStatus", paymentServiceImpl.findByOrder(orderInfoId).status());
		modelAndView.setViewName("toss/transactions");
		return modelAndView;
	}

	/**
	 * 결제 취소 페이지로 이동
	 * @param orderInfoId 주문 보안 아이디
	 * @return 결제 취소 페이지 이동
	 */
	@GetMapping("/cancel/{order_info_id}")
	public ModelAndView paymentCancel(@PathVariable("order_info_id") String orderInfoId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("orderInfoId", orderInfoId);
		modelAndView.setViewName("toss/cancel");
		return modelAndView;
	}

	/**
	 * 주문 보안 아이디로 결제 취소
	 * @param orderInfoId 주문 보안 아이디
	 * @param cancelTextRequest 결제 취소 사유
	 * @return 결제 조회 페이지로 이동
	 */
	@PostMapping("/cancel/{order_info_id}")
	public ModelAndView paymentCancel(@PathVariable("order_info_id") String orderInfoId, @Valid @ModelAttribute
	CancelTextRequest cancelTextRequest) {
		GetOrderByInfoResponse order = orderServiceImpl.findByOrderInfoId(orderInfoId);
		if (order.price().equals(new BigDecimal("0.00"))) {
			CancelResponse cancelResponse = paymentServiceImpl.paymentFindByOrderInfoId(orderInfoId);
			paymentServiceImpl.cancelPointSalePayment(cancelResponse.paymentId());
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:/api/payments/transactions/" + orderInfoId);
			return modelAndView;
		}
		CancelResponse cancelResponse = paymentServiceImpl.paymentFindByOrderInfoId(orderInfoId);

		String url = "https://api.tosspayments.com/v1/payments/" + cancelResponse.paymentKey() + "/cancel";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", keyManagerService.getSecret("176afe7c8c07476cb319873dbb99af00"));
		headers.setContentType(MediaType.APPLICATION_JSON);

		// 요청 본문 데이터 생성
		String requestBody = String.format("{\"cancelReason\":\"%s\"}", cancelTextRequest.reason());

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = paymentRestTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		String responseBody = response.getBody();

		paymentServiceImpl.updatePayment(responseBody, cancelResponse.paymentId());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("paymentInfo", paymentServiceImpl.transactions(responseBody));
		modelAndView.setViewName("toss/transactions");
		return modelAndView;
	}

}
