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

@Controller
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class AddressController {
	private final AddressService addressService;

	@GetMapping("/my-address")
	public String addressPage(Model model) {
		model.addAttribute("myAddresses", addressService.getAddresses().getBody());
		return "address/address";
	}

	@PostMapping
	public String addAddress(
		@ModelAttribute RegisterAddressRequest registerAddressRequest,
		RedirectAttributes redirectAttributes
	) {
		addressService.registerAddress(registerAddressRequest);
		redirectAttributes.addFlashAttribute("responseMessage", "주소가 등록되었습니다.");
		return "redirect:/addresses/my-address";
	}

	@DeleteMapping("/{addressId}")
	public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId, Model model) {
		ResponseEntity<Void> deleteAddressResopnse = addressService.deleteAddress(addressId);
		model.addAttribute("responseMessage", "주소가 삭제되었습니다.");
		return deleteAddressResopnse;
	}

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

	@PutMapping("/{addressId}/default")
	public ResponseEntity<Void> setDefaultAddress(@PathVariable Long addressId, Model model) {
		ResponseEntity<Void> setDefaultAddressResponse = addressService.setDefaultAddress(addressId);
		model.addAttribute("responseMessage", "기본 주소가 설정되었습니다.");
		return setDefaultAddressResponse;
	}
}
