package com.nhnacademy.bookstorefront.address.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.address.dto.request.RegisterAddressRequest;
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
	public String addAddress(@ModelAttribute RegisterAddressRequest registerAddressRequest) {
		addressService.registerAddress(registerAddressRequest);
		return "redirect:/addresses/my-address";
		// TODO: 10개 초과 시 에러 처리
	}
}
