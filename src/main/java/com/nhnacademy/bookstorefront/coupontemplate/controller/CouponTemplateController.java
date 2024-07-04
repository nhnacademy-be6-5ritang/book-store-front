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
	public String getAllCouponTemplates(@PageableDefault(page=1, size = 3)Pageable pageable, Model model) {
		Page<CouponTemplateResponseDTO> coupons = couponTemplateService.getAllCouponTemplatesByManagerPaging(pageable);
		int blockLimit = 3;
		int startPage = 1;
		int endPage = 1;


		if (!coupons.isEmpty()) {
			// 검색 결과가 있는 경우에만 페이지 번호 계산
			startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
			endPage = Math.min((startPage + blockLimit - 1), coupons.getTotalPages());
		}

		model.addAttribute("coupons", coupons);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);


		return "coupon-manager/coupon-template";
	}




	// 쿠폰발급페이지 페이징처리
	@GetMapping("/issue")
	public String getAllCouponTemplatesIssuePaging(@PageableDefault(page=1, size = 3)Pageable pageable,Model model) {
		Page<CouponTemplateResponseDTO> couponTemplates = couponTemplateService.getAllCouponTemplatesByUserPaging(pageable);
		int blockLimit = 3;
		int startPage = 1;
		int endPage = 1;


		if (!couponTemplates.isEmpty()) {
			// 검색 결과가 있는 경우에만 페이지 번호 계산
			startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
			endPage = Math.min((startPage + blockLimit - 1), couponTemplates.getTotalPages());
		}

		model.addAttribute("coupontemplates", couponTemplates);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "coupon-user/user-coupon-issue";
	}


}
