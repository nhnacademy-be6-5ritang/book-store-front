package com.nhnacademy.bookstorefront.deliverypolicy.controller;

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

import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.CreateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.UpdateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPoliciesResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.service.DeliveryPolicyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/deliveries-policies")
public class DeliveryPolicyController {
	private final DeliveryPolicyService deliveryPolicyService;

	@GetMapping("/create")
	public ModelAndView createDeliveryPolicyForm() {
		return new ModelAndView("/deliverypolicy/create-delivery-policy");
	}

	@GetMapping("/update/{deliveryPolicyId}")
	public ModelAndView updateDeliveryPolicyForm(@PathVariable Long deliveryPolicyId) {
		ModelAndView modelAndView = new ModelAndView("/deliverypolicy/update-delivery-policy");
		modelAndView.addObject("deliveryPolicy", deliveryPolicyService.getDeliveryPolicy(deliveryPolicyId));
		return modelAndView;
	}

	@GetMapping("/{deliveryPolicyId}")
	public ModelAndView getDeliveryPolicy(@PathVariable Long deliveryPolicyId) {
		ModelAndView modelAndView = new ModelAndView("deliverypolicy/get-delivery-policy");
		GetDeliveryPolicyResponse response = deliveryPolicyService.getDeliveryPolicy(deliveryPolicyId);
		modelAndView.addObject("deliveryPolicy", response);
		return modelAndView;
	}

	@GetMapping
	public ModelAndView listDeliveryPolicies() {
		ModelAndView modelAndView = new ModelAndView("deliverypolicy/list-delivery-policy");
		List<GetDeliveryPoliciesResponse> responses = deliveryPolicyService.getDeliveryPolicies();
		modelAndView.addObject("deliveryPolicies", responses);
		return modelAndView;
	}

	@PostMapping
	public String createDeliveryPolicy(@ModelAttribute CreateDeliveryPolicyRequest request){
		deliveryPolicyService.createDeliveryPolicy(request);
		return "redirect:/deliveries-policies";
	}

	@PutMapping("/{deliveryPolicyId}")
	public String updateDeliveryPolicy(@PathVariable Long deliveryPolicyId, @ModelAttribute UpdateDeliveryPolicyRequest request){
		deliveryPolicyService.updateDeliveryPolicy(deliveryPolicyId, request);
		return "redirect:/deliveries-policies";
	}

	@DeleteMapping("/{deliveryPolicyId}")
	public String deleteDeliveryPolicy(@PathVariable Long deliveryPolicyId) {
		deliveryPolicyService.deleteDeliveryPolicy(deliveryPolicyId);
		return "redirect:/deliveries-policies";
	}
}
