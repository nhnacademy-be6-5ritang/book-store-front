package com.nhnacademy.bookstorefront.order.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.service.impl.BookServiceImpl;
import com.nhnacademy.bookstorefront.bookcart.dto.response.GetBookCartResponse;
import com.nhnacademy.bookstorefront.bookcart.service.BookCartService;
import com.nhnacademy.bookstorefront.delivery.dto.response.GetDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.service.impl.DeliveryServiceImpl;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.service.impl.DeliveryPolicyServiceImpl;
import com.nhnacademy.bookstorefront.order.dto.request.CreateBookOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.CreateCartOrderPost;
import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderListPost;
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
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.NoCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.OneCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponOrderResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.service.UserAndCouponService;

import jakarta.servlet.http.HttpServletResponse;
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
	private final UserAndCouponService userAndCouponService;
	private final BookCartService bookCartService;

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
	public ModelAndView createOrder(@PathVariable("order_list_id") Long orderListId, @PathVariable("delivery_id") Long deliveryId, @RequestParam(value = "couponId", required = false) Long couponId) {
		ModelAndView modelAndView = new ModelAndView();


		// 비회원인경우
		if(couponId==null){

			GetBookOrderResponse bookOrder = bookOrderServiceImpl.getBookOrder(orderListId);
			GetListWrappingResponse list = wrappingPaperServiceImpl.getWrappingPaperByOrderListId(orderListId);

			GetUserPointOrderResponse point = orderServiceImpl.getUserPoint();

			BigDecimal deliveryQuantity = new BigDecimal(bookOrder.quantity());
			deliveryQuantity = bookOrder.getBookResponse().bookPrice().multiply(deliveryQuantity);
			GetDeliveryPolicyResponse delivery=deliveryPolicyServiceImpl.findByDeliveryPolicyStandardPriceLessThanEqualOrderByDeliveryPolicyStandardPriceDesc(deliveryId, deliveryQuantity);

			GetListWrappingResponse wrappingList= wrappingPaperServiceImpl.getWrappingPaperByOrderListId(orderListId);

			BigDecimal total = BigDecimal.ZERO;

			for (GetWrappingResponse getWrappingResponse : list.wrapping()) {
				BigDecimal paperQuantity = new BigDecimal(getWrappingResponse.quantity());
				BigDecimal multiply = getWrappingResponse.price().multiply(paperQuantity);
				total = total.add(multiply);
			}

			NoCouponResponseDTO noResult=userAndCouponService.noCouponReturnModel(bookOrder, delivery, wrappingList);

			modelAndView.addObject("noResultByNone", noResult);
			modelAndView.addObject("orderList", bookOrder);
			modelAndView.addObject("orderListId", orderListId);
			modelAndView.addObject("deliveryId", deliveryId);
			modelAndView.addObject("wrappingList", wrappingList);
			modelAndView.addObject("total", total);
			modelAndView.addObject("delivery", delivery);
			modelAndView.addObject("point", point);
			modelAndView.setViewName("order/checkout");


			//회원인 경우
		} else {


			GetBookOrderResponse bookOrder = bookOrderServiceImpl.getBookOrder(orderListId);
			GetListWrappingResponse list = wrappingPaperServiceImpl.getWrappingPaperByOrderListId(orderListId);

			GetUserPointOrderResponse point = orderServiceImpl.getUserPoint();

			BigDecimal deliveryQuantity = new BigDecimal(bookOrder.quantity());
			deliveryQuantity = bookOrder.getBookResponse().bookPrice().multiply(deliveryQuantity);
			GetDeliveryPolicyResponse delivery=deliveryPolicyServiceImpl.findByDeliveryPolicyStandardPriceLessThanEqualOrderByDeliveryPolicyStandardPriceDesc(deliveryId, deliveryQuantity);

			GetListWrappingResponse wrappingList= wrappingPaperServiceImpl.getWrappingPaperByOrderListId(orderListId);

			BigDecimal total = BigDecimal.ZERO;

			for (GetWrappingResponse getWrappingResponse : list.wrapping()) {
				BigDecimal paperQuantity = new BigDecimal(getWrappingResponse.quantity());
				BigDecimal multiply = getWrappingResponse.price().multiply(paperQuantity);
				total = total.add(multiply);
			}
			// 쿠폰 선택한경우
			if(couponId!=0){
				UserAndCouponOrderResponseDTO selectCoupon = userAndCouponService.getSelectedCouponByOrder(couponId);
				OneCouponResponseDTO oneResult=userAndCouponService.oneCouponReturnModel(selectCoupon, bookOrder, delivery, wrappingList);
				modelAndView.addObject("selectCoupon", selectCoupon);
				modelAndView.addObject("oneResult", oneResult);

				// 쿠폰선택하지 않은경우
			} else {
				NoCouponResponseDTO noResult=userAndCouponService.noCouponReturnModel(bookOrder, delivery, wrappingList);
				modelAndView.addObject("noResult", noResult);

			}


			modelAndView.addObject("orderList", bookOrder);
			modelAndView.addObject("orderListId", orderListId);
			modelAndView.addObject("deliveryId", deliveryId);
			modelAndView.addObject("wrappingList", wrappingList);
			modelAndView.addObject("total", total);
			modelAndView.addObject("delivery", delivery);
			modelAndView.addObject("point", point);
			modelAndView.setViewName("order/checkout");
		}



		return modelAndView;
	}


   // TODO : coupon 사용됨처리
	@PostMapping("/complete/{order_list_id}/{delivery_id}")
	public String createOrder(@ModelAttribute CreateOrderRequest createOrderRequest,
		@PathVariable("order_list_id") Long orderListId, @PathVariable("delivery_id") Long deliveryId
	) {
		CreateOrderResponse createOrderResponse = orderServiceImpl.createOrder(createOrderRequest);
		// TODO : 쿠폰아이디 어떻게 가져오지? orderListId가 아니라 couponId 줘야함
		userAndCouponService.updateCouponAfterPayment(orderListId);
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

	@GetMapping("/complete/{orderInfoId}")
	public ModelAndView completeCartOrder(@PathVariable String orderInfoId) {
		List<GetBookOrderResponse> list = bookOrderServiceImpl.getBookOrderByOrderId(orderInfoId);
		for (GetBookOrderResponse getBookOrderResponse : list) {
			bookServiceImpl.updateQuantity(getBookOrderResponse.getBookResponse().bookId(), getBookOrderResponse.quantity());
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("order", orderServiceImpl.findByOrderInfoId(orderInfoId));
		modelAndView.addObject("bookOrder", list);
		modelAndView.setViewName("order/order-cart-complete");
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

	//TODO 카트 주문

	@GetMapping("/cart-order")
	public String cartOrder(@CookieValue(name = "cartId", required = false) String cartId, HttpServletResponse response) {
		CreateCartOrderResponse orderId = orderServiceImpl.createCartOrder();
		List<GetBookCartResponse> list = bookCartService.getBookCartsByCartId(cartId, response);

		for (GetBookCartResponse getBookCartResponse : list) {
			bookOrderServiceImpl.createBookOrder(new CreateBookOrderRequest(getBookCartResponse.bookId(),orderId.orderId(),getBookCartResponse.bookQuantity()));
		}

		return "redirect:/api/orders/cart-order/wrapping/"+ orderId.orderInfoId();
	}

	@GetMapping("/cart-order/wrapping/{orderInfoId}")
	public ModelAndView cartOrderWrappingGet(@PathVariable String orderInfoId) {
		ModelAndView modelAndView = new ModelAndView("cart-order/selectPaper");
		List<GetBookOrderResponse> bookOrders = bookOrderServiceImpl.getBookOrderByOrderId(orderInfoId);
		GetAllPaperResponse getAllPaperResponse = paperTypeServiceImpl.getAllPaperTypes();
		modelAndView.addObject("getAllPaperResponse", getAllPaperResponse.papers());
		modelAndView.addObject("orderInfoId", orderInfoId);
		modelAndView.addObject("bookOrder", bookOrders);
		return modelAndView;
	}

	@PostMapping("/cart-order/wrapping/{orderInfoId}")
	public String cartOrderWrappingPost(@ModelAttribute CreateCartOrderPost createOrderListPost, @PathVariable String orderInfoId) {
		List<GetBookOrderResponse> bookOrders = bookOrderServiceImpl.getBookOrderByOrderId(orderInfoId);
		if (createOrderListPost.paperId() == null) {
			for (GetBookOrderResponse aLong : bookOrders) {
				wrappingPaperServiceImpl.createWrappingPapers(6L, aLong.orderListId(), aLong.quantity());
			}
		} else {
			// 수량이 허용된 한도를 초과하지 않은 경우 wrapping paper 생성
			for (int i = 0; i < bookOrders.size(); i++) {
				for (int j = 0; j < bookOrders.get(i).quantity(); j++) {
					wrappingPaperServiceImpl.createWrappingPapers(
						createOrderListPost.paperId().get(i), bookOrders.get(i).orderListId(), 1);
				}
			}
		}
		return "redirect:/api/deliveries/cart-order/"+ orderInfoId;
	}

	@GetMapping("/createOrderTest/{delivery_id}/cart/{orderInfoId}")
	public ModelAndView createOrder(@PathVariable("orderInfoId") String orderInfoId, @PathVariable("delivery_id") Long deliveryId, @RequestParam(value = "couponId", required = false) Long couponId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("orderListId", bookOrderServiceImpl.getBookOrderByOrderId(orderInfoId));

		// 비회원인경우
		if(couponId==null){
			List<GetBookOrderResponse> bookOrders = bookOrderServiceImpl.getBookOrderByOrderId(orderInfoId);

			// 결과물을 담을 리스트 생성
			List<GetListWrappingResponse> wrappingListResults = new ArrayList<>();
			BigDecimal wrappingTotal = BigDecimal.ZERO;
			BigDecimal orderTotal = BigDecimal.ZERO;

			for (GetBookOrderResponse bookOrderList : bookOrders) {

				BigDecimal deliveryQuantity = new BigDecimal(bookOrderList.quantity());
				deliveryQuantity = bookOrderList.getBookResponse().bookPrice().multiply(deliveryQuantity);

				GetListWrappingResponse wrappingList= wrappingPaperServiceImpl.getWrappingPaperByOrderListId(bookOrderList.orderListId());

				for (GetWrappingResponse getWrappingResponse : wrappingList.wrapping()) {
					BigDecimal paperQuantity = new BigDecimal(getWrappingResponse.quantity());
					BigDecimal multiply = getWrappingResponse.price().multiply(paperQuantity);
					wrappingTotal = wrappingTotal.add(multiply);
				}

				wrappingListResults.add(wrappingList);
				orderTotal = orderTotal.add(deliveryQuantity);
			}
			GetDeliveryPolicyResponse delivery = deliveryPolicyServiceImpl.findByDeliveryPolicyStandardPriceLessThanEqualOrderByDeliveryPolicyStandardPriceDesc(deliveryId, orderTotal);

			NoCouponResponseDTO noResult = userAndCouponService.noCouponReturnModelCart(orderTotal, delivery.deliveryPolicyPrice() ,wrappingTotal) ;

			GetUserPointOrderResponse point = orderServiceImpl.getUserPoint();
			modelAndView.addObject("orderList", bookOrders);
			modelAndView.addObject("deliveryId", deliveryId);
			modelAndView.addObject("wrappingList", wrappingListResults);
			modelAndView.addObject("delivery", orderTotal);
			modelAndView.addObject("point", point);
			modelAndView.addObject("noResultByNone", noResult);
			modelAndView.setViewName("order/checkout-cart-order");

			//회원인 경우
		} else {
			List<GetBookOrderResponse> bookOrders = bookOrderServiceImpl.getBookOrderByOrderId(orderInfoId);

			// 결과물을 담을 리스트 생성
			List<GetListWrappingResponse> wrappingListResults = new ArrayList<>();
			BigDecimal wrappingTotal = BigDecimal.ZERO;
			BigDecimal orderTotal = BigDecimal.ZERO;

			for (GetBookOrderResponse bookOrderList : bookOrders) {

				BigDecimal deliveryQuantity = new BigDecimal(bookOrderList.quantity());
				deliveryQuantity = bookOrderList.getBookResponse().bookPrice().multiply(deliveryQuantity);

				GetListWrappingResponse wrappingList= wrappingPaperServiceImpl.getWrappingPaperByOrderListId(bookOrderList.orderListId());

				for (GetWrappingResponse getWrappingResponse : wrappingList.wrapping()) {
					BigDecimal paperQuantity = new BigDecimal(getWrappingResponse.quantity());
					BigDecimal multiply = getWrappingResponse.price().multiply(paperQuantity);
					wrappingTotal = wrappingTotal.add(multiply);
				}

				wrappingListResults.add(wrappingList);
				orderTotal = orderTotal.add(deliveryQuantity);
			}
			GetDeliveryPolicyResponse delivery = deliveryPolicyServiceImpl.findByDeliveryPolicyStandardPriceLessThanEqualOrderByDeliveryPolicyStandardPriceDesc(deliveryId, orderTotal);


			// 쿠폰 선택한경우
			if(couponId!=0){
				UserAndCouponOrderResponseDTO selectCoupon = userAndCouponService.getSelectedCouponByOrder(couponId);
				OneCouponResponseDTO oneResult=userAndCouponService.oneCouponReturnModelCart(selectCoupon, orderTotal, delivery.deliveryPolicyPrice(), wrappingTotal);
				modelAndView.addObject("selectCoupon", selectCoupon);
				modelAndView.addObject("oneResult", oneResult);

				// 쿠폰선택하지 않은경우
			} else {
				NoCouponResponseDTO noResult=userAndCouponService.noCouponReturnModelCart(orderTotal, delivery.deliveryPolicyPrice(), wrappingTotal);
				modelAndView.addObject("noResult", noResult);

			}


			GetUserPointOrderResponse point = orderServiceImpl.getUserPoint();

			modelAndView.addObject("orderList", bookOrders);
			modelAndView.addObject("orderInfoId", orderInfoId);
			modelAndView.addObject("deliveryId", deliveryId);
			modelAndView.addObject("wrappingList", wrappingListResults);
			modelAndView.addObject("delivery", delivery.deliveryPolicyPrice());
			modelAndView.addObject("point", point);
			modelAndView.setViewName("order/checkout-cart-order");
		}



		return modelAndView;
	}


	// TODO : coupon 사용됨처리
	@PostMapping("/complete/cart-order/{orderInfoId}/{delivery_id}")
	public String createCartOrder(@ModelAttribute CreateOrderRequest createOrderRequest,
		@PathVariable("orderInfoId") String orderInfoId, @PathVariable("delivery_id") Long deliveryId
	) {
		List<GetBookOrderResponse> getBookOrderResponses = bookOrderServiceImpl.getBookOrderByOrderId(orderInfoId);
		CreateOrderResponse createOrderResponse = orderServiceImpl.updateCartOrder(createOrderRequest, getBookOrderResponses.getFirst()
			.orderId());
		// TODO : 쿠폰아이디 어떻게 가져오지? orderListId가 아니라 couponId 줘야함
		userAndCouponService.updateCouponAfterPayment(createOrderResponse.orderId());
		deliveryServiceImpl.updateDeliveryAddOrder(deliveryId, getBookOrderResponses.getFirst().orderId());
		return "redirect:/api/payments/" + createOrderResponse.infoId();
	}

}
