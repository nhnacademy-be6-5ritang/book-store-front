package com.nhnacademy.bookstorefront.userandcoupon.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request.UserAndCouponRequestCreateDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request.UserAndCouponRequestUpdateDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.feignclient.UserAndCouponFeignClient;
import com.nhnacademy.bookstorefront.userandcoupon.service.UserAndCouponService;

@Service
public class UserAndCouponServiceImpl implements UserAndCouponService {

	private final UserAndCouponFeignClient userAndCouponFeignClient;

	public UserAndCouponServiceImpl(UserAndCouponFeignClient userAndCouponFeignClient) {
		this.userAndCouponFeignClient = userAndCouponFeignClient;
	}

	@Override
	public UserAndCouponResponseDTO createUserAndCoupon(Long couponTemplateId) {
		Long userId=2L;
		UserAndCouponRequestCreateDTO requestDTO = new UserAndCouponRequestCreateDTO(userId, false);

		return userAndCouponFeignClient.createUserAndCoupon(couponTemplateId ,requestDTO).getBody();
	}

	@Override
	public UserAndCouponResponseDTO updateUserAndCoupon(Long userId, UserAndCouponRequestUpdateDTO requestDTO) {
		return userAndCouponFeignClient.updateUserAndCoupon(userId, requestDTO).getBody();
	}


	// @Override
	// public Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(Pageable pageable) {
	// 	return userAndCouponFeignClient.getAllUserAndCouponPaging(pageable).getBody();
	// }

	@Override
	public Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(Long userId, String type, Pageable pageable) {
		return userAndCouponFeignClient.getAllUserAndCouponPaging(userId, type, pageable).getBody();
	}


	@Override
	public Page<UserAndCouponResponseDTO> getUserAndCouponByIdPaging(Long userId, Pageable pageable) {
		return userAndCouponFeignClient.getUserAndCouponByIdPaging(userId, pageable).getBody();

	}

}


