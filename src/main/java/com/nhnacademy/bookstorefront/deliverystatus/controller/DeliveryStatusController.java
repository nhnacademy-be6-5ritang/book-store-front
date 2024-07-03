package com.nhnacademy.bookstorefront.deliverystatus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.deliverystatus.dto.request.CreateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.request.UpdateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.service.DeliveryStatusService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/deliveryStatuses")
public class DeliveryStatusController {
	private final DeliveryStatusService deliveryStatusService;

	@GetMapping("/create")
	public String createDeliveryStatusForm() {
		return "deliveryStatus/create-delivery-status";
	}

	@GetMapping("/update/{deliveryStatusId}")
	public String updateDeliveryStatusForm(@PathVariable Long deliveryStatusId, Model model) {
		model.addAttribute("deliveryStatus", deliveryStatusService.getDeliveryStatus(deliveryStatusId));
		return "deliveryStatus/update-delivery-status";
	}

	@GetMapping
	public String listDeliveryStatuses(Model model) {
		model.addAttribute("deliveryStatuses", deliveryStatusService.getDeliveryStatuses());
		return "deliveryStatus/list-delivery-status";
	}

	@PostMapping
	public String createDeliveryStatus(@ModelAttribute CreateDeliveryStatusRequest request) {
		deliveryStatusService.createDeliveryStatus(request);
		return "redirect:/api/deliveryStatuses";
	}

	@PutMapping("/{deliveryStatusId}")
	public String updateDeliveryStatus(@PathVariable Long deliveryStatusId,
		@ModelAttribute UpdateDeliveryStatusRequest request) {
		deliveryStatusService.updateDeliveryStatus(deliveryStatusId, request);
		return "redirect:/api/deliveryStatuses";
	}

	@DeleteMapping("/{deliveryStatusId}")
	public String deleteDeliveryStatus(@PathVariable Long deliveryStatusId) {
		deliveryStatusService.deleteDeliveryStatus(deliveryStatusId);
		return "redirect:/api/deliveryStatuses";
	}
}
