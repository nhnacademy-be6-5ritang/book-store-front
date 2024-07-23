package com.nhnacademy.bookstorefront.couponpolicy.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
import com.nhnacademy.bookstorefront.couponpolicy.exception.CouponBookIdNullException;
import com.nhnacademy.bookstorefront.couponpolicy.exception.CouponCategoryIdNullException;
import com.nhnacademy.bookstorefront.couponpolicy.exception.CouponPolicyTypeIsNotExist;
import com.nhnacademy.bookstorefront.couponpolicy.exception.CouponPolicyValidationException;
import com.nhnacademy.bookstorefront.couponpolicy.service.impl.CouponPolicyServiceImpl;
import com.nhnacademy.bookstorefront.global.controller.payload.ErrorStatus;
import com.nhnacademy.bookstorefront.global.util.PagingModel;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/coupons/policies")
public class CouponPolicyController {
	private final CouponPolicyServiceImpl couponPolicyService;

	public CouponPolicyController(CouponPolicyServiceImpl couponPolicyService) {
		this.couponPolicyService = couponPolicyService;
	}

	@PostMapping
	public String createCouponPolicy(
		@Valid @ModelAttribute  CouponPolicyCreateRequestDTO requestDTO) {

			// SalePrice와 SaleRate 유효성 검사 추가
			if (requestDTO.salePrice() == null && requestDTO.saleRate() == null && requestDTO.maxSalePrice() == null
				|| requestDTO.salePrice() != null && requestDTO.saleRate() != null && requestDTO.maxSalePrice() != null
				|| requestDTO.salePrice() != null && requestDTO.saleRate() != null
				|| requestDTO.salePrice() != null && requestDTO.maxSalePrice() != null) {
				ErrorStatus errorStatus = ErrorStatus.from("쿠폰 정책등록시 할인가격은 할인률, 최대할인가격과 함께 등록할 수 없습니다.", HttpStatus.BAD_REQUEST, LocalDateTime.now());
				throw new CouponPolicyValidationException(errorStatus);
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
						ErrorStatus errorStatus = ErrorStatus.from("책쿠폰 정책등록시 book id가 필요합니다.", HttpStatus.BAD_REQUEST, LocalDateTime.now());
						throw new CouponBookIdNullException(errorStatus);
					}
					couponPolicyService.issueBookCoupon(requestDTO);
					break;
				case "category":
					if (requestDTO.categoryId() == null) {
						ErrorStatus errorStatus = ErrorStatus.from("카테고리쿠폰 정책등록시 category id가 필요합니다.", HttpStatus.BAD_REQUEST, LocalDateTime.now());
						throw new CouponCategoryIdNullException(errorStatus);
					}
					couponPolicyService.issueCategoryCoupon(requestDTO);
					break;
				case "sale":
					couponPolicyService.issueSaleCoupon(requestDTO);
					break;
				default:
					ErrorStatus errorStatus = ErrorStatus.from( "해당 쿠폰 타입은 등록할 수 없습니다.", HttpStatus.BAD_REQUEST, LocalDateTime.now());
					throw new CouponPolicyTypeIsNotExist(errorStatus);
			}

		return "redirect:/coupons/policies";
	}

	@PatchMapping("/{couponPolicyId}")
	public String updateCouponPolicy(@PathVariable("couponPolicyId") Long couponPolicyId,
	@Valid	@ModelAttribute CouponPolicyUpdateRequestDTO requestDTO) {

			// SalePrice와 SaleRate 유효성 검사 추가
			if (requestDTO.salePrice() == null && requestDTO.saleRate() == null && requestDTO.maxSalePrice() == null
				|| requestDTO.salePrice() != null && requestDTO.saleRate() != null && requestDTO.maxSalePrice() != null
				|| requestDTO.salePrice() != null && requestDTO.saleRate() != null
				|| requestDTO.salePrice() != null && requestDTO.maxSalePrice() != null) {
				ErrorStatus errorStatus = ErrorStatus.from("쿠폰 정책등록시 할인가격은 할인률, 최대할인가격과 함께 등록할 수 없습니다.", HttpStatus.BAD_REQUEST, LocalDateTime.now());
				throw new CouponPolicyValidationException(errorStatus);
			}

			couponPolicyService.updateCouponPolicy(couponPolicyId, requestDTO);

		return "redirect:/coupons/policies";
	}

	@GetMapping
	public String getCouponPolicies(@PageableDefault(page = 1, size = 3) Pageable pageable, Model model) {
			Page<CouponPolicyResponseDTO> policies = couponPolicyService.getAllCouponPolicies(pageable);
		PagingModel.pagingProcessing(pageable, model, policies, "/coupons/policies", 5);
		model.addAttribute("policies", policies);

		return "coupon-manager/coupon-policy"; // Ensure this view exists
	}


}