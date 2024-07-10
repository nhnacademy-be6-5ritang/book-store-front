package com.nhnacademy.bookstorefront.couponpolicy.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

			// SalePrice와 SaleRate 유효성 검사 추가
			if ((requestDTO.salePrice() == null && requestDTO.saleRate() == null) ||
				(requestDTO.salePrice() != null && requestDTO.saleRate() != null)) {
				throw new IllegalArgumentException("Either salePrice or saleRate must be provided exclusively.");
			}


			switch (requestDTO.type().toLowerCase()) {
				case "welcome":
					couponPolicyService.issueWelcomeCoupon(requestDTO);
					break;
				case "birthday":
					couponPolicyService.issueBirthdayCoupon(requestDTO);
					break;
				case "book":
					if (requestDTO.bookId() == null) {
						throw new IllegalArgumentException("Book ID is required for book coupons");
					}
					couponPolicyService.issueBookCoupon(requestDTO);
					break;
				case "category":
					if (requestDTO.categoryId() == null) {
						throw new IllegalArgumentException("Category ID is required for category coupons");
					}
					couponPolicyService.issueCategoryCoupon(requestDTO);
					break;
				case "sale":
					couponPolicyService.issueSaleCoupon(requestDTO);
					break;
				default:
					throw new IllegalArgumentException("Invalid coupon type: " + requestDTO.type());
			}

			model.addAttribute("message", "Coupon policy created successfully!");
		} catch (Exception e) {
			model.addAttribute("error", "Error creating coupon policy: " + e.getMessage());
		}
		return "redirect:/coupons/policies";
	}

	@PatchMapping("/{couponPolicyId}")
	public String updateCouponPolicy(@PathVariable("couponPolicyId") Long couponPolicyId,
		@ModelAttribute CouponPolicyUpdateRequestDTO requestDTO, Model model) {
		try {

			// SalePrice와 SaleRate 유효성 검사 추가
			if ((requestDTO.salePrice() == null && requestDTO.saleRate() == null) ||
				(requestDTO.salePrice() != null && requestDTO.saleRate() != null)) {
				throw new IllegalArgumentException("Either salePrice or saleRate must be provided exclusively.");
			}
			couponPolicyService.updateCouponPolicy(couponPolicyId, requestDTO);
			model.addAttribute("message", "Coupon policy updated successfully!");
		} catch (Exception e) {
			model.addAttribute("error", "Error updating coupon policy: " + e.getMessage());
		}
		return "redirect:/coupons/policies";
	}

	@GetMapping
	public String getCouponPolicies(@PageableDefault(page = 1, size = 3) Pageable pageable, Model model) {
		try {
			Page<CouponPolicyResponseDTO> policies = couponPolicyService.getAllCouponPolicies(pageable);
			int blockLimit = 3;
			int startPage = 1; // 1 4 7 10 ~~
			int endPage = 1;

			if (!policies.isEmpty()) {
				int adjustedPage = Math.max(pageable.getPageNumber(), 1);
				startPage = (((int)(Math.ceil((double)adjustedPage / blockLimit))) - 1) * blockLimit + 1;
				endPage = Math.min((startPage + blockLimit - 1), policies.getTotalPages());
			}

			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("policies", policies);

		} catch (Exception e) {
			model.addAttribute("error", "Error fetching coupon policies: " + e.getMessage());
		}
		return "coupon-manager/coupon-policy"; // Ensure this view exists
	}
}