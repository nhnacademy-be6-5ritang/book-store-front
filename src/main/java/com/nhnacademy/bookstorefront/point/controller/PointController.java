package com.nhnacademy.bookstorefront.point.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.point.dto.request.CreatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.request.UpdatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.response.GetAllPointTransactionResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointTransactionResponse;
import com.nhnacademy.bookstorefront.point.service.impl.PointEarningPolicyServiceImpl;
import com.nhnacademy.bookstorefront.point.service.impl.PointTransactionServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class PointController {
	private final PointEarningPolicyServiceImpl pointEarningPolicyService;
	private final PointTransactionServiceImpl pointTransactionService;

	@GetMapping("/point-earning-policies/admin")
	public ModelAndView earningPoliciesAdmin() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("policies", pointEarningPolicyService.getPointEarningPolicies());
		modelAndView.setViewName("point/policyAdmin");
		return modelAndView;
	}

	@GetMapping("/point-earning-policies/admin/{policyId}/active")
	public ModelAndView earningPoliciesAdminActive(@PathVariable Long policyId) {
		pointEarningPolicyService.activatePointEarningPolicy(policyId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("policies", pointEarningPolicyService.getPointEarningPolicies());
		modelAndView.setViewName("point/policyAdmin");
		return modelAndView;
	}

	@GetMapping("/point-earning-policies/admin/{policyId}/deactivate")
	public ModelAndView earningPoliciesAdminDeactivate(@PathVariable Long policyId) {
		pointEarningPolicyService.deactivatePointEarningPolicy(policyId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("policies", pointEarningPolicyService.getPointEarningPolicies());
		modelAndView.setViewName("point/policyAdmin");
		return modelAndView;
	}

	@GetMapping("/point-earning-policies/admin/create")
	public ModelAndView createPointEarningPolicy() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("point/create");
		return modelAndView;
	}

	@PostMapping("/point-earning-policies/admin")
	public ModelAndView createPointEarningPolicy(@Valid @ModelAttribute CreatePointEarningPolicyRequest request) {
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
	public ModelAndView updatePointEarningPolicy(@PathVariable Long policyId,
		@Valid @ModelAttribute UpdatePointEarningPolicyRequest request) {
		pointEarningPolicyService.updatePointEarningPolicy(policyId, request);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/api/point-earning-policies/admin");
		return modelAndView;
	}

	@GetMapping("/point-transactions")
	public ModelAndView transactions(@PageableDefault(page = 1) Pageable pageable) {
		Page<GetPointTransactionResponse> pointTransactions = pointTransactionService.getPointTransactions(pageable);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pointTransactions", pointTransactions);
		modelAndView.addObject("objects", pointTransactions); // 공통 객체 이름
		modelAndView.addObject("baseUrl", "/api/point-transactions"); // 페이징 URL

		int blockLimit = 3;
		int startPage =
			(((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
		int endPage = Math.min((startPage + blockLimit - 1), pointTransactions.getTotalPages());

		modelAndView.addObject("pageable", pageable);
		modelAndView.addObject("blockLimit", blockLimit);
		modelAndView.addObject("startPage", startPage);
		modelAndView.addObject("endPage", endPage);

		modelAndView.setViewName("point/transactions");
		return modelAndView;
	}

	@GetMapping("/point-transactions/admin")
	public ModelAndView transactionsAdmin(@PageableDefault(page = 1) Pageable pageable) {
		Page<GetAllPointTransactionResponse> pointTransactions = pointTransactionService.getAllPointTransactions(
			pageable);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pointTransactions", pointTransactions);
		modelAndView.addObject("objects", pointTransactions); // 공통 객체 이름
		modelAndView.addObject("baseUrl", "/api/point-transactions/admin"); // 페이징 URL

		int blockLimit = 3;
		int startPage =
			(((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
		int endPage = Math.min((startPage + blockLimit - 1), pointTransactions.getTotalPages());

		modelAndView.addObject("pageable", pageable);
		modelAndView.addObject("blockLimit", blockLimit);
		modelAndView.addObject("startPage", startPage);
		modelAndView.addObject("endPage", endPage);

		modelAndView.setViewName("point/transactionsAdmin");
		return modelAndView;
	}

	@GetMapping("/point/admin")
	public ModelAndView pointAdmin() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("point/admin");
		return modelAndView;
	}

}
