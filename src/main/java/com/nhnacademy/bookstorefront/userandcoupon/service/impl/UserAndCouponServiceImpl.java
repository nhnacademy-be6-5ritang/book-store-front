package com.nhnacademy.bookstorefront.userandcoupon.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
	public void createUserAndCoupon(Long couponTemplateId) {

		 userAndCouponFeignClient.createUserAndCoupon(couponTemplateId);

	}

	@Override
	public void createWelcomeCoupon(Long userId) {

		userAndCouponFeignClient.createUserWelcomeCouponIssue(userId);

	}




	@Override
	public Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(Long userId, String type, Pageable pageable) {
		return userAndCouponFeignClient.getAllUsersAndCouponsByManagerPaging(userId, type, pageable).getBody();
	}


	@Override
	public Page<UserAndCouponResponseDTO> getUserAndCouponByIdPaging(Pageable pageable) {
		return userAndCouponFeignClient.getAllUserAndCouponsByUserPaging(pageable).getBody();

	}
	//
	//
	//
	// @Override
	// public Page<UserAndCouponResponseDTO> getUserAndCouponByIdPaging(Long userId, Pageable pageable) {
	// 	return userAndCouponFeignClient.getAllUserAndCouponsByUserPaging(userId, pageable).getBody();
	//
	// }

}


