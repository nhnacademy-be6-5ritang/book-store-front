package com.nhnacademy.bookstorefront.couponpolicy.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyCreateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyUpdateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.response.CouponPolicyResponseDTO;
import com.nhnacademy.bookstorefront.couponpolicy.service.impl.CouponPolicyServiceImpl;

@Controller
@RequestMapping("/coupons/policies")
public class CouponPolicyController {
	private final CouponPolicyServiceImpl couponPolicyService;



	public CouponPolicyController(CouponPolicyServiceImpl couponPolicyService) {
		this.couponPolicyService = couponPolicyService;
	}


	@PostMapping
	public String createCouponPolicy(
		@ModelAttribute CouponPolicyCreateRequestDTO requestDTO,
		Model model) {
		try {
			CouponPolicyResponseDTO response = switch (requestDTO.type().toLowerCase()) {
				case "welcome" -> couponPolicyService.issueWelcomeCoupon(requestDTO);
				case "birthday" -> couponPolicyService.issueBirthdayCoupon(requestDTO);
				case "book" -> {
					if (requestDTO.bookId() == null) {
						throw new IllegalArgumentException("Book ID is required for book coupons");
					}
					yield couponPolicyService.issueBookCoupon(requestDTO.bookId(), requestDTO);
				}
				case "category" -> {
					if (requestDTO.categoryId() == null) {
						throw new IllegalArgumentException("Category ID is required for category coupons");
					}
					yield couponPolicyService.issueCategoryCoupon(requestDTO.categoryId(), requestDTO);
				}
				case "sale" -> couponPolicyService.issueSaleCoupon(requestDTO);
				default -> throw new IllegalArgumentException("Invalid coupon type: " + requestDTO.type());
			};
			model.addAttribute("response", response);
			model.addAttribute("message", "Coupon policy created successfully!");
		} catch (Exception e) {
			model.addAttribute("error", "Error creating coupon policy: " + e.getMessage());
		}
		return "redirect:/coupons/policies";
	}


	@PatchMapping("/{couponPolicyId}")
	public String updateCouponPolicy(@PathVariable("couponPolicyId") Long couponPolicyId, @ModelAttribute CouponPolicyUpdateRequestDTO requestDTO , Model model) {
		try {
			CouponPolicyResponseDTO response = couponPolicyService.updateCouponPolicy(couponPolicyId, requestDTO);
			model.addAttribute("response", response);
			model.addAttribute("message", "Coupon policy updated successfully!");
		} catch (Exception e) {
			model.addAttribute("error", "Error updating coupon policy: " + e.getMessage());
		}
		return "redirect:/coupons/policies";
	}



	// TODO : logic짤때 도서, 카테고리 쿠폰의 경우 해당 쿠폰 도서명, 카테고리명도 나오게끔 해야함.
	@GetMapping
	public String getCouponPolicies(Model model) {
		try {
			List<CouponPolicyResponseDTO> policies = couponPolicyService.getAllCouponPolicies();
			model.addAttribute("policies", policies);
		} catch (Exception e) {
			model.addAttribute("error", "Error fetching coupon policies: " + e.getMessage());
		}
		return "/coupon-manager/coupon-policy"; // Ensure this view exists
	}




}
