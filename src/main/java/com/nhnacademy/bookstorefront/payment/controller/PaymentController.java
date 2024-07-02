package com.nhnacademy.bookstorefront.payment.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.payment.dto.request.PaymentConfirmationRequest;
import com.nhnacademy.bookstorefront.payment.dto.response.GetBookOrderByInfoIdResponse;
import com.nhnacademy.bookstorefront.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;
	private final RestTemplate restTemplate;

	@GetMapping("{order_info_id}")
	public ModelAndView payment(@PathVariable("order_info_id") String orderInfoId) {
		GetOrderByInfoResponse orderInfo = paymentService.findByOrder(orderInfoId);
		GetBookOrderByInfoIdResponse bookOrder =paymentService.findByOrderInfoId(orderInfoId);
		ModelAndView view = new ModelAndView();
		view.addObject("orderName", bookOrder.getBookResponse().bookTitle());
		view.addObject("orderId", orderInfoId);
		view.addObject("price", orderInfo.price());
		view.addObject("payerName", orderInfo.payername());
		view.setViewName("toss/toss");
		return view;
	}

	@GetMapping("/success")
	public ModelAndView paymentSuccess(@RequestParam String orderId, @RequestParam String paymentKey,
		@RequestParam String amount) {

		String apiUrl = "https://api.tosspayments.com/v1/payments/confirm";
		String authToken = "Basic dGVzdF9za19BUTkyeW14TjM0MjllTUtFSmVManJhalJLWHZkOg==";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", authToken);

		HttpEntity<PaymentConfirmationRequest> entity = new HttpEntity<>(
			PaymentConfirmationRequest.form(paymentKey, Integer.parseInt(amount), orderId), headers);
		String response = restTemplate.postForObject(apiUrl, entity, String.class);

		paymentService.savePaymentResponse(response);
		GetOrderByInfoResponse order = paymentService.findByOrder(orderId);
		GetBookOrderByInfoIdResponse bookOrder = paymentService.findByOrderInfoId(orderId);
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:/complete/" + bookOrder.orderListId() + "/" + order.orderId());
		return view;
	}
	@GetMapping("/fail")
	public ModelAndView paymentFail() {
		ModelAndView view = new ModelAndView();

		//나중에 메인 페이지로 바꿀예정
		view.setViewName("toss/fail");
		return view;
	}

	@GetMapping("/transactions/{order_info_id}")
	public ModelAndView paymentTransactions(@PathVariable("order_info_id") String orderInfoId) {
		String url = "https://api.tosspayments.com/v1/payments/orders/" + orderInfoId;

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic dGVzdF9za19BUTkyeW14TjM0MjllTUtFSmVManJhalJLWHZkOg==");

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		String responseBody = response.getBody();

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("paymentInfo", paymentService.transactions(responseBody));
		modelAndView.setViewName("toss/transactions");
		return modelAndView;
	}
}