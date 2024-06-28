package com.nhnacademy.bookstorefront.coupontemplate.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.request.CouponTemplateCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.response.CouponTemplateResponseDTO;
import com.nhnacademy.bookstorefront.coupontemplate.service.CouponTemplateService;

@Controller
@RequestMapping("/coupons")
public class CouponTemplateController {

	private final CouponTemplateService couponTemplateService;

	public CouponTemplateController(CouponTemplateService couponTemplateService) {
		this.couponTemplateService = couponTemplateService;
	}

	@PostMapping
	public String createCoupon(@ModelAttribute CouponTemplateCreateRequestDTO requestDTO) {
		couponTemplateService.createCouponTemplate(requestDTO);
	 return "redirect:/coupons/policies";
	}



	@GetMapping
	public String getAllCouponTemplates(Model model) {
		List<CouponTemplateResponseDTO> coupons = couponTemplateService.getAllCouponTemplates();
		model.addAttribute("coupons", coupons);
		return "/coupon-manager/coupon-template";
	}


	// @GetMapping("/issue")
	// public String getAllCouponsIssuePage(Model model) {
	// 	List<CouponResponseDTO> coupons = couponService.getAllCoupons();
	// 	model.addAttribute("coupons", coupons);
	// 	return "user-coupon-issue";
	// }


	// 쿠폰발급페이지 페이징처리
	@GetMapping("/issue")
	public String getAllCouponTemplatesIssuePaging(@PageableDefault(page=1)Pageable pageable,Model model) {
		Page<CouponTemplateResponseDTO> couponTemplates = couponTemplateService.getAllCouponTemplatesPaging(pageable);
		int blockLimit = 3;
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
		int endPage = Math.min((startPage + blockLimit - 1), couponTemplates.getTotalPages());


		model.addAttribute("coupontemplates", couponTemplates);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "/coupon-user/user-coupon-issue";
	}


}
