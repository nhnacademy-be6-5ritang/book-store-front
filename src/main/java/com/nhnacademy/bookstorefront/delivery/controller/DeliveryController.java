package com.nhnacademy.bookstorefront.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.delivery.service.DeliveryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/deliveries")
public class DeliveryController {
	private final DeliveryService deliveryService;


}
