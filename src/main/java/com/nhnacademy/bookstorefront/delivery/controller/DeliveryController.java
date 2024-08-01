package com.nhnacademy.bookstorefront.delivery.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.address.dto.response.GetAddressResponse;
import com.nhnacademy.bookstorefront.address.service.AddressService;
import com.nhnacademy.bookstorefront.delivery.dto.request.CreateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryByOrderIdRequest;
import com.nhnacademy.bookstorefront.delivery.dto.response.CreateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.GetDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.service.DeliveryService;
import com.nhnacademy.bookstorefront.global.util.PagingModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author 이경헌, 김다운
 * DeliveryController는 배송 관련 HTTP 요청을 처리하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryController {
	private final DeliveryService deliveryService;
	private final AddressService addressService;

	/**
	 * 사용자의 배송 목록을 페이지네이션하여 조회합니다.
	 *
	 * @param pageable 페이지 정보 (페이지 번호와 페이지 크기)
	 * @param model    스프링 모델 객체
	 * @return 배송 목록 페이지의 뷰 이름
	 */
	@GetMapping("/me/page")
	public String getDeliveriesPage(@PageableDefault(page = 1, size = 10) Pageable pageable, Model model) {
		Page<GetDeliveryResponse> deliveries = deliveryService.getDeliveriesByUserId(pageable);
		model.addAttribute("deliveries", deliveries);
		PagingModel.pagingProcessing(pageable, model, deliveries, "/api/deliveries/me/page", 5);
		return "delivery/list-delivery";
	}

	/**
	 * 주어진 배송 ID에 해당하는 배송 상세 정보를 조회합니다.
	 *
	 * @param deliveryId 배송 ID
	 * @param model      스프링 모델 객체
	 * @return 배송 상세 정보 페이지의 뷰 이름
	 */
	@GetMapping("/delivery/{deliveryId}")
	public String getDelivery(@PathVariable Long deliveryId, Model model) {
		model.addAttribute("delivery", deliveryService.getDelivery(deliveryId));
		return "delivery/get-delivery";
	}

	/**
	 * 주어진 주문 목록 ID에 대한 배송 페이지를 반환합니다.
	 *
	 * @param orderListId 주문 목록 ID
	 * @return 배송 페이지의 {@link ModelAndView} 객체
	 */
	@GetMapping("{order_list_id}")
	public ModelAndView getDeliveriesPage(@PathVariable("order_list_id") Long orderListId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("orderListId", orderListId);
		if (addressService.getDefaultAddress().isPresent()) {
			Optional<GetAddressResponse> address = addressService.getDefaultAddress();
			address.ifPresent(addressResponse -> modelAndView.addObject("address", addressResponse));
		}
		modelAndView.setViewName("delivery/delivery");
		return modelAndView;
	}

	/**
	 * 주어진 주문 목록 ID에 대해 새로운 배송을 생성합니다.
	 *
	 * @param createDeliveryRequest 생성할 배송 정보 DTO
	 * @param orderListId           주문 목록 ID
	 * @return 생성된 배송 후 리다이렉트할 URL
	 */
	@PostMapping("{order_list_id}")
	public String createDeliveriesPage(@Valid @ModelAttribute CreateDeliveryRequest createDeliveryRequest,
		@PathVariable("order_list_id") Long orderListId) {
		CreateDeliveryResponse response = deliveryService.createDelivery(createDeliveryRequest);
		return "redirect:/coupons/orders/" + orderListId + "/users/" + response.deliveryId();
	}

	/**
	 * 주어진 주문 정보 ID에 대한 장바구니 주문 배송 페이지를 반환합니다.
	 *
	 * @param orderInfoId 주문 정보 ID
	 * @return 장바구니 주문 배송 페이지의 {@link ModelAndView} 객체
	 */
	@GetMapping("/cart-order/{orderInfoId}")
	public ModelAndView getDeliveriesCartOrder(@PathVariable String orderInfoId) {
		ModelAndView modelAndView = new ModelAndView();
		if (addressService.getDefaultAddress().isPresent()) {
			Optional<GetAddressResponse> address = addressService.getDefaultAddress();
			address.ifPresent(addressResponse -> modelAndView.addObject("address", addressResponse));
		}
		modelAndView.setViewName("delivery/delivery-cart");
		modelAndView.addObject("orderInfoId", orderInfoId);
		return modelAndView;
	}

	/**
	 * 주어진 주문 정보 ID에 대해 새로운 장바구니 주문 배송을 생성합니다.
	 *
	 * @param createDeliveryRequest 생성할 배송 정보 DTO
	 * @param orderInfoId           주문 정보 ID
	 * @return 생성된 배송 후 리다이렉트할 URL
	 */
	@PostMapping("/cart-order/{orderInfoId}")
	public String createDeliveriesCartOrder(@Valid @ModelAttribute CreateDeliveryRequest createDeliveryRequest,
		@PathVariable String orderInfoId) {
		CreateDeliveryResponse response = deliveryService.createDelivery(createDeliveryRequest);
		return "redirect:/coupons/orders/users/" + response.deliveryId() + "/" + orderInfoId;
	}

	/**
	 * 주어진 주문 ID에 대한 송신자 정보를 수정하는 페이지를 반환합니다.
	 *
	 * @param orderId 주문 ID
	 * @return 송신자 정보 수정 페이지의 {@link ModelAndView} 객체
	 */
	@GetMapping("/{orderId}/sender")
	public ModelAndView senderPage(@PathVariable("orderId") Long orderId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("orderId", orderId);
		modelAndView.setViewName("delivery/sender");
		return modelAndView;
	}

	/**
	 * 주어진 주문 ID에 대해 배송 정보를 업데이트합니다.
	 *
	 * @param orderId 주문 ID
	 * @param request 업데이트할 배송 정보 DTO
	 * @return 배송 정보 업데이트 후 리다이렉트할 URL
	 */
	@PostMapping("/{orderId}/sender")
	public String updateDeliveryByOrderId(@PathVariable("orderId") Long orderId,
		@Valid @ModelAttribute UpdateDeliveryByOrderIdRequest request) {
		deliveryService.updateDeliveryByOrderId(orderId, request);
		return "redirect:/api/orders/admin/order-status/going";
	}
}

