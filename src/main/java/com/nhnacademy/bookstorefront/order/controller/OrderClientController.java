package com.nhnacademy.bookstorefront.order.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.service.impl.BookServiceImpl;
import com.nhnacademy.bookstorefront.delivery.dto.response.GetDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.service.impl.DeliveryServiceImpl;
import com.nhnacademy.bookstorefront.deliverypolicy.service.impl.DeliveryPolicyServiceImpl;
import com.nhnacademy.bookstorefront.order.dto.request.CreateBookOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderListPost;
import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.CreateRefundPolicyRequest;
import com.nhnacademy.bookstorefront.order.dto.request.OrderCheckNonRequest;
import com.nhnacademy.bookstorefront.order.dto.request.UpdateRefundPolicyRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderByStatusResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllPaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllRefundResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetListWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetRefundResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetUserPointOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.UpdateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.service.Impl.BookOrderServiceImpl;
import com.nhnacademy.bookstorefront.order.service.Impl.OrderServiceImpl;
import com.nhnacademy.bookstorefront.order.service.Impl.PaperTypeServiceImpl;
import com.nhnacademy.bookstorefront.order.service.Impl.RefundPolicyServiceImpl;
import com.nhnacademy.bookstorefront.order.service.Impl.WrappingPaperServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderClientController {
	private final BookOrderServiceImpl bookOrderServiceImpl;
	private final OrderServiceImpl orderServiceImpl;

	private final PaperTypeServiceImpl paperTypeServiceImpl;
	private final WrappingPaperServiceImpl wrappingPaperServiceImpl;
	private final DeliveryServiceImpl deliveryServiceImpl;
	private final BookServiceImpl bookServiceImpl;
	private final DeliveryPolicyServiceImpl deliveryPolicyServiceImpl;
	private final RefundPolicyServiceImpl refundPolicyServiceImpl;

	@GetMapping("/createBookOrderTest/{book_id}")
	public ModelAndView createBookOrder(@PathVariable("book_id") Long bookId) {
		ModelAndView modelAndView = new ModelAndView("order/orderList");
		GetBookDetailResponse book = bookServiceImpl.getBook(bookId);
		modelAndView.addObject("book", book);
		modelAndView.addObject("bookId", bookId);
		return modelAndView;
	}

	@PostMapping("/createBookOrderTest")
	public String createBookOrderTest(@ModelAttribute CreateBookOrderRequest request) {
		CreateBookOrderResponse createBookOrderResponse = bookOrderServiceImpl.createBookOrder(request);
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
		return "redirect:/api/deliveries/" + orderListId;
	}

	@GetMapping("/createOrderTest/{order_list_id}/{delivery_id}")
	public ModelAndView createOrder(@PathVariable("order_list_id") Long orderListId, @PathVariable("delivery_id") Long deliveryId) {
		ModelAndView modelAndView = new ModelAndView();
		GetBookOrderResponse bookOrder = bookOrderServiceImpl.getBookOrder(orderListId);
		GetListWrappingResponse list = wrappingPaperServiceImpl.getWrappingPaperByOrderListId(orderListId);
		GetUserPointOrderResponse point = orderServiceImpl.getUserPoint();
		BigDecimal total = BigDecimal.ZERO;
		for (GetWrappingResponse getWrappingResponse : list.wrapping()) {
			BigDecimal paperQuantity = new BigDecimal(getWrappingResponse.quantity());
			BigDecimal multiply = getWrappingResponse.price().multiply(paperQuantity);
			total = total.add(multiply);
		}
		BigDecimal deliveryQuantity = new BigDecimal(bookOrder.quantity());
		deliveryQuantity = bookOrder.getBookResponse().bookPrice().multiply(deliveryQuantity);
		modelAndView.addObject("orderList", bookOrder);
		modelAndView.addObject("orderListId", orderListId);
		modelAndView.addObject("deliveryId", deliveryId);
		modelAndView.addObject("wrappingList", wrappingPaperServiceImpl.getWrappingPaperByOrderListId(orderListId));
		modelAndView.addObject("total", total);
		modelAndView.addObject("delivery", deliveryPolicyServiceImpl.findByDeliveryPolicyStandardPriceLessThanEqualOrderByDeliveryPolicyStandardPriceDesc(deliveryId, deliveryQuantity));
		modelAndView.addObject("point", point);
		modelAndView.setViewName("order/checkout");
		return modelAndView;
	}

	@PostMapping("/complete/{order_list_id}/{delivery_id}")
	public String createOrder(@ModelAttribute CreateOrderRequest createOrderRequest,
		@PathVariable("order_list_id") Long orderListId, @PathVariable("delivery_id") Long deliveryId
	) {
		CreateOrderResponse createOrderResponse = orderServiceImpl.createOrder(createOrderRequest);
		deliveryServiceImpl.updateDeliveryAddOrder(deliveryId, createOrderResponse.orderId());
		bookOrderServiceImpl.updateOrder(orderListId, createOrderResponse.orderId());
		return "redirect:/api/payments/" + createOrderResponse.infoId();
	}

	@GetMapping("/complete/{order_list_id}/{order_id}")
	public ModelAndView completeOrder(@PathVariable("order_list_id") Long orderListId,
		@PathVariable("order_id") Long orderId) {

		//업데이트 빼고 오더리스트아이디로 가져오기 변경 예정
		UpdateBookOrderResponse bookOrder = bookOrderServiceImpl.updateOrder(orderListId, orderId);
		bookServiceImpl.updateQuantity(bookOrder.bookId(), bookOrder.quantity());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("bookOrder", bookOrder);
		modelAndView.setViewName("order/order-complete");
		return modelAndView;
	}

	@GetMapping("/orderCheck")
	public ModelAndView orderCheck() {
		ModelAndView modelAndView = new ModelAndView();
		//현재 카트아이디로 찾지만 로그인된 사용자의 아이디를 기준으로 찾을듯?
		GetAllListOrderResponse orders = orderServiceImpl.findAllUserId();
		modelAndView.addObject("orderList", orders);
		modelAndView.setViewName("order/orderCheck");
		return modelAndView;
	}

	@GetMapping("/orderCheck/Non")
	public ModelAndView orderCheckNon() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("order/orderCheckNon");
		return modelAndView;
	}

	@PostMapping("/orderCheck/Non")
	public ModelAndView orderCheckNon(@ModelAttribute OrderCheckNonRequest orderCheckNonRequest) {
		ModelAndView modelAndView = new ModelAndView();
		//현재 카트아이디로 찾지만 로그인된 사용자의 아이디를 기준으로 찾을듯?
		modelAndView.addObject("orderList", orderServiceImpl.findByOrderInfoIdByEmail(orderCheckNonRequest));
		modelAndView.setViewName("order/orderDetailNon");
		return modelAndView;
	}

	@GetMapping("/orderDetails/{order_info_id}")
	public ModelAndView orderDetails(@PathVariable("order_info_id") String orderInfoId) {
		ModelAndView modelAndView = new ModelAndView();
		GetOrderByInfoResponse order = orderServiceImpl.findByOrderInfoId(orderInfoId);
		GetDeliveryResponse deliveryResponse = deliveryServiceImpl.getDeliveryByOrderId(order.orderId());

		if (deliveryResponse.deliveryStatusName().equals("배송완료")) {
			GetAllRefundResponse refundResponse = refundPolicyServiceImpl.getAllRefundPolicies();
			List<GetRefundResponse> list = new ArrayList<>();
			for (int i = 0; i < refundResponse.refunds().size(); i++) {
				if (deliveryResponse.deliveryReceiverDate()
					.isAfter(LocalDateTime.now().minusDays(refundResponse.refunds().get(i).refundPolicyDate()))) {
					list.add(refundResponse.refunds().get(i));
				}
			}
			modelAndView.addObject("refundList", list);
		}

		modelAndView.addObject("order", order);
		modelAndView.addObject("delivery", deliveryResponse);
		modelAndView.setViewName("order/orderDetails");
		return modelAndView;
	}

	@GetMapping("/admin")
	public ModelAndView admin() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("order/admin");
		return modelAndView;
	}

	@GetMapping("/admin/order-status/wait")
	public ModelAndView orderStatusWait() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("order/wait");
		GetAllListOrderByStatusResponse orders = orderServiceImpl.findByOrderStatusWait();
		modelAndView.addObject("orderList", orders);
		return modelAndView;
	}

	@GetMapping("/admin/order-status/going")
	public ModelAndView orderStatusGoing() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("order/going");
		GetAllListOrderByStatusResponse orders = orderServiceImpl.findByOrderStatusGoing();
		modelAndView.addObject("orderList", orders);
		return modelAndView;
	}

	@GetMapping("/admin/order-status/complete")
	public ModelAndView orderStatusComplete() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("order/complete");
		GetAllListOrderByStatusResponse orders = orderServiceImpl.findByOrderStatusComplete();
		modelAndView.addObject("orderList", orders);
		return modelAndView;
	}

	@GetMapping("/admin/order-status/refunded")
	public ModelAndView orderStatusRefunded() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("order/refunded");
		GetAllListOrderByStatusResponse orders = orderServiceImpl.findByOrderStatusRefunded();
		modelAndView.addObject("orderList", orders);
		return modelAndView;
	}

	@GetMapping("/admin/order-status/refunding")
	public ModelAndView orderStatusRefunding() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("order/refunding");
		GetAllListOrderByStatusResponse orders = orderServiceImpl.findByOrderStatusRefunding();
		modelAndView.addObject("orderList", orders);
		return modelAndView;
	}

	@GetMapping("/refunding/{orderInfoId}")
	public ModelAndView refundingGet(@PathVariable String orderInfoId) {
		orderServiceImpl.refundingOrder(orderInfoId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/api/orders/orderDetails/" + orderInfoId);
		return modelAndView;
	}

	@GetMapping("/admin/refundPolicy")
	public ModelAndView refundAdmin() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("refundPolicy", refundPolicyServiceImpl.getAllRefundPolicies());
		modelAndView.setViewName("order/adminRefund");
		return modelAndView;
	}

	@GetMapping("/admin/refundPolicy/create")
	public ModelAndView refundAdminCreate() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("order/createRefund");
		return modelAndView;
	}

	@GetMapping("/admin/refundPolicy/update/{refundPolicyId}")
	public ModelAndView refundAdminUpdate(@PathVariable Long refundPolicyId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("refundPolicyId", refundPolicyId);
		modelAndView.setViewName("order/updateRefund");
		return modelAndView;
	}

	@PostMapping("/admin/refundPolicy")
	public ModelAndView refundAdminCreate(@ModelAttribute CreateRefundPolicyRequest refundPolicyRequest) {
		refundPolicyServiceImpl.createRefundPolicy(refundPolicyRequest);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/api/orders/admin/refundPolicy");
		return modelAndView;
	}

	@PostMapping("/admin/refundPolicy/{refundPolicyId}")
	public ModelAndView refundAdminUpdate(@ModelAttribute UpdateRefundPolicyRequest refundPolicyRequest, @PathVariable Long refundPolicyId) {
		refundPolicyServiceImpl.updateRefundPolicy(refundPolicyRequest, refundPolicyId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/api/orders/admin/refundPolicy");
		return modelAndView;
	}

	@GetMapping("/admin/refundPolicy/{refundPolicyId}")
	public ModelAndView refundAdminDelete(@PathVariable Long refundPolicyId) {
		refundPolicyServiceImpl.deleteRefundPolicy(refundPolicyId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/api/orders/admin/refundPolicy");
		return modelAndView;
	}

	@GetMapping("/admin/refunded/{orderInfoId}")
	public ModelAndView refundedGet(@PathVariable String orderInfoId) {
		orderServiceImpl.refundedOrder(orderInfoId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/api/orders/orderDetails/" + orderInfoId);
		return modelAndView;
	}

}
