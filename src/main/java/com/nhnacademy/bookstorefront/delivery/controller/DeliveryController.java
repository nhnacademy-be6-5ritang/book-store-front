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

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryController {
	private final DeliveryService deliveryService;
	private final AddressService addressService;

	@GetMapping("/me/page")
	public String getDeliveriesPage(@PageableDefault(page = 1, size = 10) Pageable pageable, Model model) {
		Page<GetDeliveryResponse> deliveries = deliveryService.getDeliveriesByUserId(pageable);
		model.addAttribute("deliveries", deliveries);
		PagingModel.pagingProcessing(pageable, model, deliveries, "/api/deliveries/me/page", 5);
		return "delivery/list-delivery";
	}

	@GetMapping("/delivery/{deliveryId}")
	public String getDelivery(@PathVariable Long deliveryId, Model model) {
		model.addAttribute("delivery", deliveryService.getDelivery(deliveryId));
		return "delivery/get-delivery";
	}

	@GetMapping("{order_list_id}")
	public ModelAndView getDeliveriesPage(@PathVariable("order_list_id") Long orderListId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("orderListId", orderListId);
		if (addressService.getDefaultAddress().isPresent()){
			Optional<GetAddressResponse> address = addressService.getDefaultAddress();
			address.ifPresent(addressResponse -> modelAndView.addObject("address", addressResponse));
		}
		modelAndView.setViewName("delivery/delivery");
		return modelAndView;
	}

	@PostMapping("{order_list_id}")
	public String createDeliveriesPage(@Valid @ModelAttribute CreateDeliveryRequest createDeliveryRequest,
		@PathVariable("order_list_id") Long orderListId) {
		CreateDeliveryResponse response = deliveryService.createDelivery(createDeliveryRequest);
		return "redirect:/coupons/orders/" + orderListId + "/users/" + response.deliveryId();
	}

	@GetMapping("/cart-order/{orderInfoId}")
	public ModelAndView getDeliveriesCartOrder(@PathVariable String orderInfoId) {
		ModelAndView modelAndView = new ModelAndView();
		if (addressService.getDefaultAddress().isPresent()){
			Optional<GetAddressResponse> address = addressService.getDefaultAddress();
			address.ifPresent(addressResponse -> modelAndView.addObject("address", addressResponse));
		}
		modelAndView.setViewName("delivery/delivery-cart");
		modelAndView.addObject("orderInfoId", orderInfoId);
		return modelAndView;
	}

	@PostMapping("/cart-order/{orderInfoId}")
	public String createDeliveriesCartOrder(@Valid @ModelAttribute CreateDeliveryRequest createDeliveryRequest,
		@PathVariable String orderInfoId) {
		CreateDeliveryResponse response = deliveryService.createDelivery(createDeliveryRequest);
		return "redirect:/coupons/orders/users/" + response.deliveryId() + "/" + orderInfoId;
	}

	@GetMapping("/{orderId}/sender")
	public ModelAndView senderPage(@PathVariable("orderId") Long orderId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("orderId", orderId);
		modelAndView.setViewName("delivery/sender");
		return modelAndView;
	}

	@PostMapping("/{orderId}/sender")
	public String updateDeliveryByOrderId(@PathVariable("orderId") Long orderId,
		@Valid @ModelAttribute UpdateDeliveryByOrderIdRequest request) {
		deliveryService.updateDeliveryByOrderId(orderId, request);
		return "redirect:/api/orders/admin/order-status/going";
	}

}

