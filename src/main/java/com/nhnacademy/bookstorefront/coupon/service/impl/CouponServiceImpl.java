package com.nhnacademy.bookstorefront.coupon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.coupon.domain.dto.request.CouponCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupon.domain.dto.response.CouponResponseDTO;
import com.nhnacademy.bookstorefront.coupon.feignclient.CouponFeignClient;
import com.nhnacademy.bookstorefront.coupon.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {
	private final CouponFeignClient couponFeignClient;

	public CouponServiceImpl(CouponFeignClient couponFeignClient) {
		this.couponFeignClient = couponFeignClient;
	}

	@Override
	public CouponResponseDTO createCoupon(CouponCreateRequestDTO requestDTO) {
		return couponFeignClient.createCoupon(requestDTO).getBody();

	}

	@Override
	public List<CouponResponseDTO> getAllCoupons() {
		return couponFeignClient.getAllCoupons().getBody();
	}
}