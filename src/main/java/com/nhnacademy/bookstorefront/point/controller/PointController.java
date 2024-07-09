package com.nhnacademy.bookstorefront.point.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.point.dto.request.CreatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.request.UpdatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.service.impl.PointEarningPolicyServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class PointController {
	private final PointEarningPolicyServiceImpl pointEarningPolicyService;
	@GetMapping("/point-earning-policies/admin")
	public ModelAndView earningPoliciesAdmin() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("policies", pointEarningPolicyService.getPointEarningPolicies());
		modelAndView.setViewName("point/admin");
		return modelAndView;
	}

	@GetMapping("/point-earning-policies/admin/{policyId}/active")
	public ModelAndView earningPoliciesAdminActive(@PathVariable Long policyId) {
		pointEarningPolicyService.activatePointEarningPolicy(policyId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("policies", pointEarningPolicyService.getPointEarningPolicies());
		modelAndView.setViewName("point/admin");
		return modelAndView;
	}
	@GetMapping("/point-earning-policies/admin/{policyId}/deactivate")
	public ModelAndView earningPoliciesAdminDeactivate(@PathVariable Long policyId) {
		pointEarningPolicyService.deactivatePointEarningPolicy(policyId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("policies", pointEarningPolicyService.getPointEarningPolicies());
		modelAndView.setViewName("point/admin");
		return modelAndView;
	}

	@GetMapping("/point-earning-policies/admin/create")
	public ModelAndView createPointEarningPolicy() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("point/create");
		return modelAndView;
	}
	@PostMapping("/point-earning-policies/admin")
	public ModelAndView createPointEarningPolicy(@ModelAttribute CreatePointEarningPolicyRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		pointEarningPolicyService.createPointEarningPolicy(request);
		modelAndView.setViewName("redirect:/api/point-earning-policies/admin");
		return modelAndView;
	}

	@GetMapping("/point-earning-policies/admin/{policyId}")
	public ModelAndView earningPoliciesAdmin(@PathVariable Long policyId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("policyId", policyId);
		modelAndView.setViewName("point/update");
		return modelAndView;
	}

	@PostMapping("/point-earning-policies/admin/{policyId}")
	public ModelAndView updatePointEarningPolicy(@PathVariable Long policyId, @ModelAttribute UpdatePointEarningPolicyRequest request) {
		pointEarningPolicyService.updatePointEarningPolicy(policyId, request);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/api/point-earning-policies/admin");
		return modelAndView;
	}
}
