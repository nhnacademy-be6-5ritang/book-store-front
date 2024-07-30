package com.nhnacademy.bookstorefront.address.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhnacademy.bookstorefront.address.dto.request.RegisterAddressRequest;
import com.nhnacademy.bookstorefront.address.dto.request.UpdateAddressRequest;
import com.nhnacademy.bookstorefront.address.service.AddressService;

import lombok.RequiredArgsConstructor;

/**
 * @author 김태환
 * 주소 관련 웹 요청을 처리하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class AddressController {
	private final AddressService addressService;

	/**
	 * 사용자의 모든 주소를 조회하여 주소 페이지를 반환합니다.
	 *
	 * @param model 모델 객체
	 * @return 주소 페이지의 뷰 이름
	 */
	@GetMapping("/my-address")
	public String addressPage(Model model) {
		model.addAttribute("myAddresses", addressService.getAddresses().getBody());
		return "address/address";
	}

	/**
	 * 새로운 주소를 등록합니다.
	 *
	 * @param registerAddressRequest 주소 등록 요청 정보가 포함된 {@link RegisterAddressRequest} DTO
	 * @param redirectAttributes 리다이렉트 속성에 메시지를 추가하기 위한 {@link RedirectAttributes} 객체
	 * @return 주소 페이지로 리다이렉트하는 뷰 이름
	 */
	@PostMapping
	public String addAddress(
		@ModelAttribute RegisterAddressRequest registerAddressRequest,
		RedirectAttributes redirectAttributes
	) {
		addressService.registerAddress(registerAddressRequest);
		redirectAttributes.addFlashAttribute("responseMessage", "주소가 등록되었습니다.");
		return "redirect:/addresses/my-address";
	}

	/**
	 * 주어진 주소 ID에 해당하는 주소를 삭제합니다.
	 *
	 * @param addressId 삭제할 주소의 ID
	 * @param model 모델 객체
	 * @return 삭제 결과를 나타내는 {@link ResponseEntity} 객체
	 */
	@DeleteMapping("/{addressId}")
	public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId, Model model) {
		ResponseEntity<Void> deleteAddressResopnse = addressService.deleteAddress(addressId);
		model.addAttribute("responseMessage", "주소가 삭제되었습니다.");
		return deleteAddressResopnse;
	}

	/**
	 * 주어진 주소 ID에 해당하는 주소를 수정합니다.
	 *
	 * @param addressId 수정할 주소의 ID
	 * @param updateAddressRequest 주소 수정 요청 정보가 포함된 {@link UpdateAddressRequest} DTO
	 * @param redirectAttributes 리다이렉트 속성에 메시지를 추가하기 위한 {@link RedirectAttributes} 객체
	 * @return 주소 페이지로 리다이렉트하는 뷰 이름
	 */
	@PostMapping("/{addressId}")
	public String updateAddress(
		@PathVariable Long addressId,
		@ModelAttribute UpdateAddressRequest updateAddressRequest,
		RedirectAttributes redirectAttributes
	) {
		addressService.updateAddress(addressId, updateAddressRequest);
		redirectAttributes.addFlashAttribute("responseMessage", "주소가 수정되었습니다.");
		return "redirect:/addresses/my-address";
	}

	/**
	 * 주어진 주소 ID를 기본 주소로 설정합니다.
	 *
	 * @param addressId 기본 주소로 설정할 주소의 ID
	 * @param model 모델 객체
	 * @return 기본 주소 설정 결과를 나타내는 {@link ResponseEntity} 객체
	 */
	@PutMapping("/{addressId}/default")
	public ResponseEntity<Void> setDefaultAddress(@PathVariable Long addressId, Model model) {
		ResponseEntity<Void> setDefaultAddressResponse = addressService.setDefaultAddress(addressId);
		model.addAttribute("responseMessage", "기본 주소가 설정되었습니다.");
		return setDefaultAddressResponse;
	}
}
