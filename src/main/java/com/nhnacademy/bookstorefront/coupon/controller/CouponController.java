package com.nhnacademy.bookstorefront.coupon.controller;

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

import com.nhnacademy.bookstorefront.coupon.domain.dto.request.CouponCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupon.domain.dto.response.CouponResponseDTO;
import com.nhnacademy.bookstorefront.coupon.service.CouponService;

@Controller
@RequestMapping("/coupons")
public class CouponController {

	private final CouponService couponService;

	public CouponController(CouponService couponService) {
		this.couponService = couponService;
	}

	@PostMapping
	public String createCoupon(@ModelAttribute CouponCreateRequestDTO requestDTO) {
		couponService.createCoupon(requestDTO);
	 return "redirect:/coupons/policies";
	}



	@GetMapping
	public String getAllCoupons(Model model) {
		List<CouponResponseDTO> coupons = couponService.getAllCoupons();
		model.addAttribute("coupons", coupons);
		return "coupon";
	}


	// @GetMapping("/issue")
	// public String getAllCouponsIssuePage(Model model) {
	// 	List<CouponResponseDTO> coupons = couponService.getAllCoupons();
	// 	model.addAttribute("coupons", coupons);
	// 	return "user-coupon-issue";
	// }


	// 쿠폰발급페이지 페이징처리
	@GetMapping("/issue")
	public String getAllCouponsIssuePaging(@PageableDefault(page=1)Pageable pageable,Model model) {
		Page<CouponResponseDTO> coupons = couponService.getAllCouponsPaging(pageable);
		int blockLimit = 3;
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
		int endPage = Math.min((startPage + blockLimit - 1), coupons.getTotalPages());


		model.addAttribute("coupons", coupons);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "user-coupon-issue";
	}


}
