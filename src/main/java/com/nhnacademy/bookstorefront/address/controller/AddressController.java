package com.nhnacademy.bookstorefront.address.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class AddressController {

	@GetMapping("/my-address")
	public String addressPage() {
		return "address/address";
	}
}
