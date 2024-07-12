package com.nhnacademy.bookstorefront.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.delivery.dto.request.CreateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryByOrderIdRequest;
import com.nhnacademy.bookstorefront.delivery.dto.response.CreateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.service.DeliveryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryController {
	private final DeliveryService deliveryService;

	@GetMapping("{order_list_id}")
	public ModelAndView getDeliveriesPage(@PathVariable("order_list_id") Long orderListId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("orderListId", orderListId);
		modelAndView.setViewName("delivery/delivery");
		return modelAndView;
	}

	@PostMapping("{order_list_id}")
	public String createDeliveriesPage(@ModelAttribute CreateDeliveryRequest createDeliveryRequest,@PathVariable("order_list_id") Long orderListId) {
		CreateDeliveryResponse response = deliveryService.createDelivery(createDeliveryRequest);
		return "redirect:/coupons/orders/" + orderListId + "/users/" + response.deliveryId();
	}




	@GetMapping("/{orderId}/sender")
	public ModelAndView senderPage(@PathVariable("orderId") Long orderId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("orderId", orderId);
		modelAndView.setViewName("delivery/sender");
		return modelAndView;
	}

	@PostMapping("/{orderId}/sender")
	public String updateDeliveryByOrderId(@PathVariable("orderId") Long orderId, @ModelAttribute UpdateDeliveryByOrderIdRequest request) {
		deliveryService.updateDeliveryByOrderId(orderId, request);
		return "redirect:/api/orders/admin/order-status/going";
	}


}
