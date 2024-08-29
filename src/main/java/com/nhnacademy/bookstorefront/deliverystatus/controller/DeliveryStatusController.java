package com.nhnacademy.bookstorefront.deliverystatus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.deliverystatus.dto.request.CreateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.request.UpdateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.service.DeliveryStatusService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author 이경헌
 * 배송 상태 관련 요청을 처리하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/delivery-statuses")
public class DeliveryStatusController {
	private final DeliveryStatusService deliveryStatusService;
	private static final String REDIRECT_URL = "redirect:/delivery-statuses";

	/**
	 * 새로운 배송 상태 생성 폼을 반환합니다.
	 *
	 * @return 새로운 배송 상태 생성 폼의 경로
	 */
	@GetMapping("/create")
	public String createDeliveryStatusForm() {
		return "delivery-status/create-delivery-status";
	}

	/**
	 * 주어진 배송 상태 ID에 해당하는 배송 상태를 수정하는 폼을 반환합니다.
	 *
	 * @param deliveryStatusId 수정할 배송 상태의 ID
	 * @param model             Model 객체
	 * @return 배송 상태 수정 폼의 경로
	 */
	@GetMapping("/update/{deliveryStatusId}")
	public String updateDeliveryStatusForm(@PathVariable Long deliveryStatusId, Model model) {
		model.addAttribute("deliveryStatus", deliveryStatusService.getDeliveryStatus(deliveryStatusId));
		return "delivery-status/update-delivery-status";
	}

	/**
	 * 모든 배송 상태 목록을 조회하여 화면에 표시합니다.
	 *
	 * @param model Model 객체
	 * @return 배송 상태 목록 화면의 경로
	 */
	@GetMapping
	public String listDeliveryStatuses(Model model) {
		model.addAttribute("deliveryStatuses", deliveryStatusService.getDeliveryStatuses());
		return "delivery-status/list-delivery-status";
	}

	/**
	 * 새로운 배송 상태를 생성합니다.
	 *
	 * @param request 생성할 배송 상태 정보를 담은 요청 객체
	 * @return 배송 상태 목록 화면으로 리다이렉트
	 */
	@PostMapping
	public String createDeliveryStatus(@Valid @ModelAttribute CreateDeliveryStatusRequest request) {
		deliveryStatusService.createDeliveryStatus(request);
		return REDIRECT_URL;
	}

	/**
	 * 주어진 배송 상태 ID에 해당하는 배송 상태를 수정합니다.
	 *
	 * @param deliveryStatusId 수정할 배송 상태의 ID
	 * @param request          수정할 배송 상태 정보를 담은 요청 객체
	 * @return 배송 상태 목록 화면으로 리다이렉트
	 */
	@PutMapping("/{deliveryStatusId}")
	public String updateDeliveryStatus(@PathVariable Long deliveryStatusId,
		@Valid @ModelAttribute UpdateDeliveryStatusRequest request) {
		deliveryStatusService.updateDeliveryStatus(deliveryStatusId, request);
		return REDIRECT_URL;
	}

	/**
	 * 주어진 배송 상태 ID에 해당하는 배송 상태를 삭제합니다.
	 *
	 * @param deliveryStatusId 삭제할 배송 상태의 ID
	 * @return 배송 상태 목록 화면으로 리다이렉트
	 */
	@DeleteMapping("/{deliveryStatusId}")
	public String deleteDeliveryStatus(@PathVariable Long deliveryStatusId) {
		deliveryStatusService.deleteDeliveryStatus(deliveryStatusId);
		return REDIRECT_URL;
	}
}
