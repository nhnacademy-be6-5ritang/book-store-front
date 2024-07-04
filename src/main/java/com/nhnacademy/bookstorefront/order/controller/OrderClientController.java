package com.nhnacademy.bookstorefront.order.controller;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.service.impl.BookServiceImpl;
import com.nhnacademy.bookstorefront.order.dto.request.CreateBookOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderListPost;
import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllPaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetListWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.UpdateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.service.Impl.BookOrderServiceImpl;
import com.nhnacademy.bookstorefront.order.service.Impl.OrderServiceImpl;
import com.nhnacademy.bookstorefront.order.service.Impl.PaperTypeServiceImpl;
import com.nhnacademy.bookstorefront.order.service.Impl.WrappingPaperServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderClientController {
	private final BookOrderServiceImpl bookOrderServiceImpl;
	private final OrderServiceImpl orderServiceImpl;
	private final BookServiceImpl bookService;
	private final PaperTypeServiceImpl paperTypeServiceImpl;
	private final WrappingPaperServiceImpl wrappingPaperServiceImpl;

	@GetMapping("/createBookOrderTest/{book_id}")
	public ModelAndView createBookOrder(@PathVariable("book_id") Long bookId) {
		ModelAndView modelAndView = new ModelAndView("order/orderList");
		GetBookDetailResponse book = bookService.getBook(bookId);
		modelAndView.addObject("book", book);
		modelAndView.addObject("bookId", bookId);
		return modelAndView;
	}

	@PostMapping("/createBookOrderTest")
	public String createBookOrderTest(@ModelAttribute CreateBookOrderRequest request) {
		CreateBookOrderResponse createBookOrderResponse = bookOrderServiceImpl.createBookOrder(request);
		GetBookOrderResponse bookOrder = bookOrderServiceImpl.getBookOrder(createBookOrderResponse.orderListId());
		return "redirect:/api/orders/createOrderTestPaper/" + createBookOrderResponse.orderListId();
	}

	@GetMapping("/createOrderTestPaper/{order_list_id}")
	public ModelAndView createOrderTestPaperGet(@PathVariable("order_list_id") Long orderListId) {
		ModelAndView modelAndView = new ModelAndView("order/selectPaper");
		GetAllPaperResponse getAllPaperResponse = paperTypeServiceImpl.getAllPaperTypes();
		GetBookOrderResponse bookOrder = bookOrderServiceImpl.getBookOrder(orderListId);
		modelAndView.addObject("getAllPaperResponse", getAllPaperResponse.papers());
		modelAndView.addObject("orderListId", orderListId);
		modelAndView.addObject("bookOrder", bookOrder);
		return modelAndView;
	}

	@PostMapping("/createOrderTestPaper/{order_list_id}")
	public String createOrderTestPaperPost(@PathVariable("order_list_id") Long orderListId,
		@ModelAttribute CreateOrderListPost createOrderListPost) {

		GetBookOrderResponse bookOrder = bookOrderServiceImpl.getBookOrder(orderListId);
		if (createOrderListPost.paperId() == null) {
			wrappingPaperServiceImpl.createWrappingPapers(6L, orderListId, bookOrder.quantity());
			return "redirect:/api/orders/createOrderTest2/" + orderListId;
		}

		// 수량이 허용된 한도를 초과하지 않은 경우 wrapping paper 생성
		for (int i = 0; i < createOrderListPost.paperId().size(); i++) {
			wrappingPaperServiceImpl.createWrappingPapers(
				createOrderListPost.paperId().get(i), orderListId, 1);
		}
		return "redirect:/api/orders/createOrderTest/" + orderListId;
	}

	@GetMapping("/createOrderTest/{order_list_id}")
	public ModelAndView createOrder(@PathVariable("order_list_id") Long orderListId) {
		ModelAndView modelAndView = new ModelAndView();
		GetBookOrderResponse bookOrder = bookOrderServiceImpl.getBookOrder(orderListId);
		GetListWrappingResponse list = wrappingPaperServiceImpl.getWrappingPaperByOrderListId(orderListId);
		BigDecimal total = BigDecimal.ZERO;
		for (GetWrappingResponse getWrappingResponse : list.wrapping()) {
			BigDecimal paperQuantity = new BigDecimal(getWrappingResponse.quantity());
			BigDecimal multiply = getWrappingResponse.price().multiply(paperQuantity);
			total = total.add(multiply);
		}
		modelAndView.addObject("orderList", bookOrder);
		modelAndView.addObject("orderListId", orderListId);
		modelAndView.addObject("wrappingList", wrappingPaperServiceImpl.getWrappingPaperByOrderListId(orderListId));
		modelAndView.addObject("total", total);
		modelAndView.setViewName("order/checkout");
		return modelAndView;
	}

	@PostMapping("/complete/{order_list_id}")
	public String createOrder(@ModelAttribute CreateOrderRequest createOrderRequest,
		@PathVariable("order_list_id") Long orderListId
	) {
		CreateOrderResponse createOrderResponse = orderServiceImpl.createOrder(createOrderRequest);
		bookOrderServiceImpl.updateOrder(orderListId, createOrderResponse.orderId());
		return "redirect:/api/payments/" + createOrderResponse.infoId();
	}

	@GetMapping("/complete/{order_list_id}/{order_id}")
	public ModelAndView completeOrder(@PathVariable("order_list_id") Long orderListId,
		@PathVariable("order_id") Long orderId) {

		//업데이트 빼고 오더리스트아이디로 가져오기 변경 예정
		UpdateBookOrderResponse bookOrder = bookOrderServiceImpl.updateOrder(orderListId, orderId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("bookOrder", bookOrder);
		modelAndView.setViewName("order/order-complete");
		return modelAndView;
	}

	@GetMapping("/orderCheck/{cart_id}")
	public ModelAndView orderCheck(@PathVariable("cart_id") Long cartId) {
		ModelAndView modelAndView = new ModelAndView();
		//현재 카트아이디로 찾지만 로그인된 사용자의 아이디를 기준으로 찾을듯?
		GetAllListOrderResponse orders = orderServiceImpl.findAllByCartId(cartId);
		modelAndView.addObject("orderList", orders);
		modelAndView.setViewName("order/orderCheck");
		return modelAndView;
	}

	@GetMapping("/orderDetails/{order_info_id}")
	public ModelAndView orderDetails(@PathVariable("order_info_id") String orderInfoId) {
		ModelAndView modelAndView = new ModelAndView();
		GetOrderByInfoResponse order = orderServiceImpl.findByOrderInfoId(orderInfoId);
		modelAndView.addObject("order", order);
		modelAndView.setViewName("order/orderDetails");
		return modelAndView;
	}
}
