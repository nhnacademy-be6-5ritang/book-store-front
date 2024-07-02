package com.nhnacademy.bookstorefront.order.controller;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.order.dto.request.CreateBookOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderListPost;
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
import com.nhnacademy.bookstorefront.order.service.BookOrderService;
import com.nhnacademy.bookstorefront.order.service.BookService;
import com.nhnacademy.bookstorefront.order.service.OrderService;
import com.nhnacademy.bookstorefront.order.service.PaperTypeService;
import com.nhnacademy.bookstorefront.order.service.WrappingPaperService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class OrderClientController {
	private final BookOrderService bookOrderService;
	private final OrderService orderService;
	private final BookService bookService;
	private final PaperTypeService paperTypeService;
	private final WrappingPaperService wrappingPaperService;

	@GetMapping("/createBookOrderTest/{book_id}")
	public ModelAndView createBookOrder(@PathVariable("book_id") Long bookId) {
		ModelAndView modelAndView = new ModelAndView("order/orderList");
		GetBookResponse book = bookService.findBookById(bookId);
		modelAndView.addObject("book", book);
		modelAndView.addObject("bookId", bookId);
		return modelAndView;
	}

	@PostMapping("/createBookOrderTest")
	public String createBookOrderTest(@ModelAttribute CreateBookOrderRequest request) {
		CreateBookOrderResponse createBookOrderResponse = bookOrderService.createBookOrder(request);
		GetBookOrderResponse bookOrder = bookOrderService.getBookOrder(createBookOrderResponse.orderListId());
		if (bookOrder.getBookResponse().bookPackaging()) {
			return "redirect:/createOrderTestPaper/" + createBookOrderResponse.orderListId();
		}
		return "redirect:/createOrderTest2/" + createBookOrderResponse.orderListId();
	}

	@GetMapping("/createOrderTestPaper/{order_list_id}")
	public ModelAndView createOrderTestPaperGet(@PathVariable("order_list_id") Long orderListId) {
		ModelAndView modelAndView = new ModelAndView("order/selectPaper");
		GetAllPaperResponse getAllPaperResponse = paperTypeService.getAllPaperTypes();
		modelAndView.addObject("getAllPaperResponse", getAllPaperResponse.papers());
		modelAndView.addObject("orderListId", orderListId);
		return modelAndView;
	}

	@PostMapping("/createOrderTestPaper/{order_list_id}")
	public String createOrderTestPaperPost(@PathVariable("order_list_id") Long orderListId,
		@ModelAttribute CreateOrderListPost createOrderListPost) {

		GetBookOrderResponse bookOrder = bookOrderService.getBookOrder(orderListId);
		if (createOrderListPost.paperId() == null) {
			wrappingPaperService.createWrappingPapers(6L, orderListId, bookOrder.quantity());
			return "redirect:/createOrderTest2/" + orderListId;
		}

		int count = bookOrder.quantity();
		for (int i = 0; i < createOrderListPost.paperId().size(); i++) {
			count -= createOrderListPost.quantity().get(i);
		}
		if (count < 0) {
			return "redirect:/createOrderTestPaper/" + orderListId;
		}
		// 수량이 허용된 한도를 초과하지 않은 경우 wrapping paper 생성
		for (int i = 0; i < createOrderListPost.paperId().size(); i++) {
			wrappingPaperService.createWrappingPapers(
				createOrderListPost.paperId().get(i), orderListId, createOrderListPost.quantity().get(i));
		}
		return "redirect:/createOrderTest/" + orderListId;
	}

	@GetMapping("/createOrderTest/{order_list_id}")
	public ModelAndView createOrder(@PathVariable("order_list_id") Long orderListId) {
		ModelAndView modelAndView = new ModelAndView();
		GetBookOrderResponse bookOrder = bookOrderService.getBookOrder(orderListId);
		GetListWrappingResponse list = wrappingPaperService.getWrappingPaperByOrderListId(orderListId);
		BigDecimal total = BigDecimal.ZERO;
		for (GetWrappingResponse getWrappingResponse : list.wrapping()) {
			BigDecimal paperQuantity = new BigDecimal(getWrappingResponse.quantity());
			BigDecimal multiply = getWrappingResponse.price().multiply(paperQuantity);
			total = total.add(multiply);
		}
		modelAndView.addObject("orderList", bookOrder);
		modelAndView.addObject("orderListId", orderListId);
		modelAndView.addObject("wrappingList", wrappingPaperService.getWrappingPaperByOrderListId(orderListId));
		modelAndView.addObject("total", total);
		modelAndView.setViewName("order/checkout2");
		return modelAndView;
	}

	@GetMapping("/createOrderTest2/{order_list_id}")
	public ModelAndView createOrder2(@PathVariable("order_list_id") Long orderListId) {
		ModelAndView modelAndView = new ModelAndView();
		Long paperTypeId = 3L;
		GetBookOrderResponse bookOrder = bookOrderService.getBookOrder(orderListId);
		GetWrappingResponse getWrappingResponse = wrappingPaperService.createWrappingPapers(paperTypeId, orderListId,
			bookOrder.quantity());
		modelAndView.addObject("orderList", bookOrderService.getBookOrder(orderListId));
		modelAndView.addObject("orderListId", orderListId);
		modelAndView.addObject("wrapping", getWrappingResponse);
		modelAndView.setViewName("order/checkout");
		return modelAndView;
	}

	@PostMapping("/complete/{order_list_id}")
	public String createOrder(@ModelAttribute CreateOrderRequest createOrderRequest,
		@PathVariable("order_list_id") Long orderListId
	) {
		CreateOrderResponse createOrderResponse = orderService.createOrder(createOrderRequest);
		bookOrderService.updateOrder(orderListId, createOrderResponse.orderId());
		return "redirect:/payments/" + createOrderResponse.infoId();
	}

	@GetMapping("/complete/{order_list_id}/{order_id}")
	public ModelAndView completeOrder(@PathVariable("order_list_id") Long orderListId,
		@PathVariable("order_id") Long orderId) {

		//업데이트 빼고 오더리스트아이디로 가져오기 변경 예정
		UpdateBookOrderResponse bookOrder = bookOrderService.updateOrder(orderListId, orderId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("bookOrder", bookOrder);
		modelAndView.setViewName("order/order-complete");
		return modelAndView;
	}

	@GetMapping("/orderCheck/{cart_id}")
	public ModelAndView orderCheck(@PathVariable("cart_id") Long cartId) {
		ModelAndView modelAndView = new ModelAndView();
		//현재 카트아이디로 찾지만 로그인된 사용자의 아이디를 기준으로 찾을듯?
		GetAllListOrderResponse orders = orderService.findAllByCartId(cartId);
		modelAndView.addObject("orderList", orders);
		modelAndView.setViewName("order/orderCheck");
		return modelAndView;
	}

	@GetMapping("/orderDetails/{order_info_id}")
	public ModelAndView orderDetails(@PathVariable("order_info_id") String orderInfoId) {
		ModelAndView modelAndView = new ModelAndView();
		GetOrderByInfoResponse order = orderService.findByOrderInfoId(orderInfoId);
		modelAndView.addObject("order", order);
		modelAndView.setViewName("order/orderDetails");
		return modelAndView;
	}
}
