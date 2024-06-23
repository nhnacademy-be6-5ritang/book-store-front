package com.nhnacademy.bookstorefront.coupon.controller;

import java.util.List;

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


	// TODO 4: 페이징처리해서 보여주기
	@GetMapping("/issue")
	public String getAllCouponsIssuePage(Model model) {
		List<CouponResponseDTO> coupons = couponService.getAllCoupons();
		model.addAttribute("coupons", coupons);
		return "user-coupon-issue";
	}


}
