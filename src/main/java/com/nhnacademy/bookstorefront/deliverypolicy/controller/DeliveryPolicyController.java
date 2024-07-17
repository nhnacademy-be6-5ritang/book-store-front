package com.nhnacademy.bookstorefront.deliverypolicy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.CreateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.UpdateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.service.DeliveryPolicyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author 이경헌
 * 배송 정책을 관리하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/deliveryPolicies")
public class DeliveryPolicyController {
	private final DeliveryPolicyService deliveryPolicyService;
	private static final String REDIRECT_URL = "redirect:/api/deliveryPolicies";

	/**
	 * 배송 정책 생성 폼을 반환합니다.
	 *
	 * @return 배송 정책 생성 폼의 뷰 경로
	 */
	@GetMapping("/create")
	public String createDeliveryPolicyForm() {
		return "/deliveryPolicy/create-delivery-policy";
	}

	/**
	 * 주어진 배송 정책 ID에 해당하는 배송 정책 수정 폼을 반환합니다.
	 *
	 * @param deliveryPolicyId 수정할 배송 정책의 ID
	 * @param model            뷰에 전달할 데이터를 담는 Model 객체
	 * @return 배송 정책 수정 폼의 뷰 경로
	 */
	@GetMapping("/update/{deliveryPolicyId}")
	public String updateDeliveryPolicyForm(@PathVariable Long deliveryPolicyId, Model model) {
		model.addAttribute("deliveryPolicy", deliveryPolicyService.getDeliveryPolicy(deliveryPolicyId));
		return "/deliveryPolicy/update-delivery-policy";
	}

	/**
	 * 주어진 배송 정책 ID에 해당하는 배송 정책을 조회합니다.
	 *
	 * @param deliveryPolicyId 조회할 배송 정책의 ID
	 * @param model            뷰에 전달할 데이터를 담는 Model 객체
	 * @return 배송 정책 조회 페이지의 뷰 경로
	 */
	@GetMapping("/{deliveryPolicyId}")
	public String getDeliveryPolicy(@PathVariable Long deliveryPolicyId, Model model) {
		model.addAttribute("deliveryPolicy", deliveryPolicyService.getDeliveryPolicy(deliveryPolicyId));
		return "deliveryPolicy/get-delivery-policy";
	}

	/**
	 * 모든 배송 정책을 조회하여 리스트로 반환합니다.
	 *
	 * @param model 뷰에 전달할 데이터를 담는 Model 객체
	 * @return 배송 정책 리스트 페이지의 뷰 경로
	 */
	@GetMapping
	public String listDeliveryPolicies(Model model) {
		model.addAttribute("deliveryPolicies", deliveryPolicyService.getDeliveryPolicies());
		return "deliveryPolicy/list-delivery-policy";
	}

	/**
	 * 새로운 배송 정책을 생성합니다.
	 *
	 * @param request 생성할 배송 정책 정보를 담은 요청 객체
	 * @return 배송 정책 리스트 페이지로 리다이렉트하는 URL
	 */
	@PostMapping
	public String createDeliveryPolicy(@Valid @ModelAttribute CreateDeliveryPolicyRequest request) {
		deliveryPolicyService.createDeliveryPolicy(request);
		return REDIRECT_URL;
	}

	/**
	 * 주어진 배송 정책 ID에 해당하는 배송 정책을 수정합니다.
	 *
	 * @param deliveryPolicyId 수정할 배송 정책의 ID
	 * @param request          수정할 배송 정책 정보를 담은 요청 객체
	 * @return 배송 정책 리스트 페이지로 리다이렉트하는 URL
	 */
	@PutMapping("/{deliveryPolicyId}")
	public String updateDeliveryPolicy(@PathVariable Long deliveryPolicyId,
		@Valid @ModelAttribute UpdateDeliveryPolicyRequest request) {
		deliveryPolicyService.updateDeliveryPolicy(deliveryPolicyId, request);
		return REDIRECT_URL;
	}

	/**
	 * 주어진 배송 정책 ID에 해당하는 배송 정책을 삭제합니다.
	 *
	 * @param deliveryPolicyId 삭제할 배송 정책의 ID
	 * @return 배송 정책 리스트 페이지로 리다이렉트하는 URL
	 */
	@DeleteMapping("/{deliveryPolicyId}")
	public String deleteDeliveryPolicy(@PathVariable Long deliveryPolicyId) {
		deliveryPolicyService.deleteDeliveryPolicy(deliveryPolicyId);
		return REDIRECT_URL;
	}
}
