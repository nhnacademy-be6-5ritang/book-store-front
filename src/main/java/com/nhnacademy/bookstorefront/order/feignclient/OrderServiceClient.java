package com.nhnacademy.bookstorefront.order.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.order.dto.request.CreateBookOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllPaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetListWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.UpdateBookOrderResponse;

@FeignClient(name = "order-feign-service", url = "http://localhost:8083")
public interface OrderServiceClient {

	@GetMapping("/books/{book_id}")
	ResponseEntity<GetBookResponse> getBook(@PathVariable("book_id") Long bookId);

	@GetMapping("/books-orders/{order_list_id}")
	ResponseEntity<GetBookOrderResponse> getBookOrder(@PathVariable("order_list_id") Long orderListId);

	//도서 주문 생성
	@PostMapping("/books-orders")
	 ResponseEntity<CreateBookOrderResponse> createBookOrder(
		@RequestBody CreateBookOrderRequest createBookOrderRequest);

	@GetMapping("/wrappings")
	 ResponseEntity<GetAllPaperResponse> getAllWrappingPapers();

	@PostMapping("/wrappings/{paper_id}/{book_order_id}/{quantity}")
	 ResponseEntity<GetWrappingResponse> createWrappingPapers(@PathVariable("paper_id") Long paperId, @PathVariable("book_order_id") long bookOrderId, @PathVariable("quantity") int quantity);

	@GetMapping("/books-orders/{order_list_id}/wrapping-papers")
	 ResponseEntity<GetListWrappingResponse> getWrappingPaperByOrderListId(@PathVariable("order_list_id") Long orderListId);

	//주문 만들기 (도서 주문)
	@PostMapping("/orders")
	 ResponseEntity<CreateOrderResponse> createOrder(@ModelAttribute CreateOrderRequest createOrderRequest);

	@PutMapping("/books-orders/{book_list_id}/{order_id}")
	 ResponseEntity<UpdateBookOrderResponse> updateBookOrder(@PathVariable("book_list_id") Long bookListId, @PathVariable("order_id") Long orderId);

	@GetMapping("/carts/{cart_id}/orders/all")
	 ResponseEntity<GetAllListOrderResponse> findAllByCartId(@PathVariable("cart_id") Long cartId);

	@GetMapping("/order-info/{order_info_id}")
	 ResponseEntity<GetOrderByInfoResponse> findByOrderInfoId(@PathVariable("order_info_id") String orderInfoId);

}
