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
	public UserAndCouponResponseDTO createUserAndCoupon(Long couponId) {
		String userEmail="2";
		UserAndCouponRequestCreateDTO requestDTO = new UserAndCouponRequestCreateDTO(userEmail, false);

		return userAndCouponFeignClient.createUserAndCoupon(couponId ,requestDTO).getBody();
	}

	@Override
	public UserAndCouponResponseDTO updateUserAndCoupon(String userEmail, UserAndCouponRequestUpdateDTO requestDTO) {
		return userAndCouponFeignClient.updateUserAndCoupon(userEmail, requestDTO).getBody();
	}

	// @Override
	// public List<UserAndCouponResponseDTO> getAllUserAndCoupons() {
	// 	return userAndCouponFeignClient.getAllUserAndCoupons().getBody();
	// }


	@Override
	public Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(Pageable pageable) {
		return userAndCouponFeignClient.getAllUserAndCouponPaging(pageable).getBody();
	}
	//
	// @Override
	// public List<UserAndCouponResponseDTO> getUserAndCouponById(String userEmail) {
	// 	return userAndCouponFeignClient.getUserAndCouponById(userEmail).getBody();
	//
	// }

	@Override
	public Page<UserAndCouponResponseDTO> getUserAndCouponByIdPaging(String userEmail, Pageable pageable) {
		return userAndCouponFeignClient.getUserAndCouponByIdPaging(userEmail, pageable).getBody();

	}

}


