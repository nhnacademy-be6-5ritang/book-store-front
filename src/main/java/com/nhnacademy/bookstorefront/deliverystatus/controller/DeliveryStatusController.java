package com.nhnacademy.bookstorefront.deliverystatus.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.deliverystatus.dto.request.CreateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.request.UpdateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.GetDeliveryStatusResponse;
import com.nhnacademy.bookstorefront.deliverystatus.service.DeliveryStatusService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/deliveries-statuses")
public class DeliveryStatusController {
	private final DeliveryStatusService deliveryStatusService;

	@GetMapping("/create")
	public ModelAndView createDeliveryStatusForm() {
		return new ModelAndView("/deliverystatus/create-delivery-status");
	}

	@GetMapping("/update/{deliveryStatusId}")
	public ModelAndView updateDeliveryStatusForm(@PathVariable Long deliveryStatusId) {
		ModelAndView modelAndView = new ModelAndView("/deliverystatus/update-delivery-status");
		modelAndView.addObject("deliveryStatus", deliveryStatusService.getDeliveryStatus(deliveryStatusId));
		return modelAndView;
	}

	@GetMapping
	public ModelAndView listDeliveryStatuses() {
		ModelAndView modelAndView = new ModelAndView("deliverystatus/list-delivery-status");
		List<GetDeliveryStatusResponse> responses = deliveryStatusService.getDeliveryStatuses();
		modelAndView.addObject("deliveryStatuses", responses);
		return modelAndView;
	}

	@PostMapping
	public String createDeliveryStatus(@ModelAttribute CreateDeliveryStatusRequest request) {
		deliveryStatusService.createDeliveryStatus(request);
		return "redirect:/deliveries-statuses";
	}

	@PutMapping("/{deliveryStatusId}")
	public String updateDeliveryStatus(@PathVariable Long deliveryStatusId,
		@ModelAttribute UpdateDeliveryStatusRequest request) {
		deliveryStatusService.updateDeliveryStatus(deliveryStatusId, request);
		return "redirect:/deliveries-statuses";
	}

	@DeleteMapping("/{deliveryStatusId}")
	public String deleteDeliveryStatus(@PathVariable Long deliveryStatusId) {
		deliveryStatusService.deleteDeliveryStatus(deliveryStatusId);
		return "redirect:/deliveries-statuses";
	}
}
