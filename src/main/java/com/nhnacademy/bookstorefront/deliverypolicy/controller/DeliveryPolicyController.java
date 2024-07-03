package com.nhnacademy.bookstorefront.deliverypolicy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.CreateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.UpdateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.service.DeliveryPolicyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/deliveryPolicies")
public class DeliveryPolicyController {
	private final DeliveryPolicyService deliveryPolicyService;

	@GetMapping("/create")
	public String createDeliveryPolicyForm() {
		return "/deliveryPolicy/create-delivery-policy";
	}

	@GetMapping("/update/{deliveryPolicyId}")
	public String updateDeliveryPolicyForm(@PathVariable Long deliveryPolicyId, Model model) {
		model.addAttribute("deliveryPolicy", deliveryPolicyService.getDeliveryPolicy(deliveryPolicyId));
		return "/deliveryPolicy/update-delivery-policy";
	}

	@GetMapping("/{deliveryPolicyId}")
	public String getDeliveryPolicy(@PathVariable Long deliveryPolicyId, Model model) {
		model.addAttribute("deliveryPolicy", deliveryPolicyService.getDeliveryPolicy(deliveryPolicyId));
		return "deliveryPolicy/get-delivery-policy";
	}

	@GetMapping
	public String listDeliveryPolicies(Model model) {
		model.addAttribute("deliveryPolicies", deliveryPolicyService.getDeliveryPolicies());
		return "deliveryPolicy/list-delivery-policy";
	}

	@PostMapping
	public String createDeliveryPolicy(@ModelAttribute CreateDeliveryPolicyRequest request){
		deliveryPolicyService.createDeliveryPolicy(request);
		return "redirect:/api/deliveryPolicies";
	}

	@PutMapping("/{deliveryPolicyId}")
	public String updateDeliveryPolicy(@PathVariable Long deliveryPolicyId, @ModelAttribute UpdateDeliveryPolicyRequest request){
		deliveryPolicyService.updateDeliveryPolicy(deliveryPolicyId, request);
		return "redirect:/api/deliveryPolicies";
	}

	@DeleteMapping("/{deliveryPolicyId}")
	public String deleteDeliveryPolicy(@PathVariable Long deliveryPolicyId) {
		deliveryPolicyService.deleteDeliveryPolicy(deliveryPolicyId);
		return "redirect:/api/deliveryPolicies";
	}
}
