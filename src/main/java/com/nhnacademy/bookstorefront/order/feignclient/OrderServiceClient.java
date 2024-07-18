package com.nhnacademy.bookstorefront.order.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.order.dto.request.CreateBookOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.CreateRefundPolicyRequest;
import com.nhnacademy.bookstorefront.order.dto.request.OrderCheckNonRequest;
import com.nhnacademy.bookstorefront.order.dto.request.UpdateRefundPolicyRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateCartOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderByStatusResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllPaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllRefundResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookByOrderCouponResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetListWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetNonOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetUserPointOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.UpdateBookOrderResponse;

@FeignClient(name = "order-feign-service", url = "http://localhost:8090")
public interface OrderServiceClient {

	@GetMapping("/api/orders/books-orders/{order_list_id}")
	ResponseEntity<GetBookOrderResponse> getBookOrder(@PathVariable("order_list_id") Long orderListId);

	//도서 주문 생성
	@PostMapping("/api/orders/books-orders")
	 ResponseEntity<CreateBookOrderResponse> createBookOrder(
		@RequestBody CreateBookOrderRequest createBookOrderRequest);

	@GetMapping("/api/orders/wrappings")
	 ResponseEntity<GetAllPaperResponse> getAllWrappingPapers();

	@PostMapping("/api/orders/wrappings/{paper_id}/{book_order_id}/{quantity}")
	 ResponseEntity<GetWrappingResponse> createWrappingPapers(@PathVariable("paper_id") Long paperId, @PathVariable("book_order_id") long bookOrderId, @PathVariable("quantity") int quantity);

	@GetMapping("/api/orders/books-orders/{order_list_id}/wrapping-papers")
	 ResponseEntity<GetListWrappingResponse> getWrappingPaperByOrderListId(@PathVariable("order_list_id") Long orderListId);

	//주문 만들기 (도서 주문)
	@PostMapping("/api/orders/orders")
	 ResponseEntity<CreateOrderResponse> createOrder(@ModelAttribute CreateOrderRequest createOrderRequest);

	@PutMapping("/api/orders/books-orders/{book_list_id}/{order_id}")
	 ResponseEntity<UpdateBookOrderResponse> updateBookOrder(@PathVariable("book_list_id") Long bookListId, @PathVariable("order_id") Long orderId);

	@GetMapping("/api/orders/users/all")
	 ResponseEntity<GetAllListOrderResponse> findAllByUserId();

	@GetMapping("/api/orders/order-info/{order_info_id}")
	 ResponseEntity<GetOrderByInfoResponse> findByOrderInfoId(@PathVariable("order_info_id") String orderInfoId);

	@GetMapping("/api/orders/order-status/wait")
	ResponseEntity<GetAllListOrderByStatusResponse> getOrderStatusWait();

	@GetMapping("/api/orders/order-status/going")
	ResponseEntity<GetAllListOrderByStatusResponse> getOrderStatusGoing();

	@GetMapping("/api/orders/order-status/complete")
	ResponseEntity<GetAllListOrderByStatusResponse> getOrderStatusComplete();

	@GetMapping("/api/orders/order-status/refunded")
	ResponseEntity<GetAllListOrderByStatusResponse> getOrderStatusRefunded();

	@GetMapping("/api/orders/order-status/refunding")
	ResponseEntity<GetAllListOrderByStatusResponse> getOrderStatusRefunding();


	@PostMapping("/api/orders/order-info/Non")
	ResponseEntity<GetNonOrderByInfoResponse> getOrderByInfoNon(@ModelAttribute OrderCheckNonRequest orderCheckNonRequest);

	@GetMapping("/api/orders/orders-points")
	ResponseEntity<GetUserPointOrderResponse> getUserPointOrders();

	@GetMapping("/api/orders/refund-policy")
	ResponseEntity<GetAllRefundResponse> getRefundPolicy();

	@PutMapping("/api/orders/refund-policy/{refundPolicyId}")
	ResponseEntity<Void> updateRefundPolicy(@PathVariable("refundPolicyId") Long refundPolicyId, @ModelAttribute UpdateRefundPolicyRequest updateRefundPolicyRequest);

	@PostMapping("/api/orders/refund-policy")
	ResponseEntity<Void> createRefundPolicy(@ModelAttribute CreateRefundPolicyRequest refundPolicyRequest);

	@DeleteMapping("/api/orders/refund-policy/{refundPolicyId}")
	ResponseEntity<Void> deleteRefundPolicy(@PathVariable Long refundPolicyId);

	@GetMapping("/api/orders/refunding/{orderInfoId}")
	ResponseEntity<Void> refundingOrder(@PathVariable("orderInfoId") String orderInfoId);

	@GetMapping("/api/orders/refunded/{orderInfoId}")
	ResponseEntity<Void> refundedOrder(@PathVariable("orderInfoId") String orderInfoId);

	@GetMapping("/api/orders/cart-orders")
	ResponseEntity<CreateCartOrderResponse> createCartOrders();

	@GetMapping("/api/orders/book-orders/cart-order/{orderInfoId}")
	ResponseEntity<List<GetBookOrderResponse>> getCartOrder(@PathVariable("orderInfoId") String orderInfoId);


	/**
	 * 단건주문 bookId, categoryId 가져오는 feignClient method
	 * @author 이기훈
	 * @param orderListId 주문리스트 Id
	 * @return 주문한 bookId, categoryId 가져옴
	 */

	@GetMapping("/api/orders/{orderListId}/book")
	ResponseEntity<GetBookByOrderCouponResponse> getBookByOneOrder(@PathVariable("orderListId") Long orderListId);

	@PutMapping("/api/orders/cart-order/{orderId}")
	ResponseEntity<CreateOrderResponse> updateCartOrder(@RequestBody CreateOrderRequest createOrderRequest, @PathVariable Long orderId);
}
