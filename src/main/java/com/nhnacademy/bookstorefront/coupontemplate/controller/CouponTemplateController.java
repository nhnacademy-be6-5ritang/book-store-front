package com.nhnacademy.bookstorefront.coupontemplate.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.request.CouponTemplateCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.response.CouponTemplateResponseDTO;
import com.nhnacademy.bookstorefront.coupontemplate.service.CouponTemplateService;
import com.nhnacademy.bookstorefront.global.util.PagingModel;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/coupons")
@Slf4j
public class CouponTemplateController {

	private final CouponTemplateService couponTemplateService;

	public CouponTemplateController(CouponTemplateService couponTemplateService) {
		this.couponTemplateService = couponTemplateService;
	}

	@PostMapping
	public String createCoupon(@Valid @ModelAttribute CouponTemplateCreateRequestDTO requestDTO) {

		couponTemplateService.createCouponTemplate(requestDTO);

		return "redirect:/coupons/policies";
	}

	@GetMapping
	public String getAllCouponTemplates(@PageableDefault(page = 1, size = 3) Pageable pageable, Model model) {
		Page<CouponTemplateResponseDTO> coupons = couponTemplateService.getAllCouponTemplatesByManagerPaging(pageable);

		PagingModel.pagingProcessing(pageable, model, coupons, "/coupons", 5);
		model.addAttribute("coupons", coupons);

		return "coupon-manager/coupon-template";
	}

	// 쿠폰발급페이지 페이징처리
	@GetMapping("/issue")
	public String getAllCouponTemplatesIssuePaging(@PageableDefault(page = 1, size = 3) Pageable pageable, @RequestParam(required = false) String message,
		Model model) {

			Page<CouponTemplateResponseDTO> couponTemplates = couponTemplateService.getAllCouponTemplatesByUserPaging(
				pageable);

			PagingModel.pagingProcessing(pageable, model, couponTemplates, "/coupons/issue", 5);

			model.addAttribute("couponTemplates", couponTemplates);

			model.addAttribute("message", message);

			return "coupon-user/user-coupon-issue";

	}
}


