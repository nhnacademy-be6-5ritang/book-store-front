package com.nhnacademy.bookstorefront.userandcoupon.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.order.dto.response.GetBookByOrderCouponResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponOrderResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.feignclient.UserAndCouponFeignClient;
import com.nhnacademy.bookstorefront.userandcoupon.service.UserAndCouponService;

@Service
public class UserAndCouponServiceImpl implements UserAndCouponService {

	private final UserAndCouponFeignClient userAndCouponFeignClient;
	private final OrderServiceClient orderServiceClient;

	public UserAndCouponServiceImpl(UserAndCouponFeignClient userAndCouponFeignClient,
		OrderServiceClient orderServiceClient) {
		this.userAndCouponFeignClient = userAndCouponFeignClient;
		this.orderServiceClient = orderServiceClient;
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

	@Override
	public List<UserAndCouponResponseDTO> getAllUserAndCouponByOrder(Long orderListId) {
		GetBookByOrderCouponResponse response = orderServiceClient.getBookByOneOrder(orderListId).getBody();
		Long bookId = Optional.ofNullable(response)
			.map(GetBookByOrderCouponResponse::bookId)
			.orElseThrow(() -> new IllegalArgumentException("bookId is null"));
		List<Long> categoryId = Optional.of(response)
			.map(GetBookByOrderCouponResponse::categoryId)
			.orElseThrow(() -> new IllegalArgumentException("categoryId is null"));
		BigDecimal bookPrice= Optional.of(response)
			.map(GetBookByOrderCouponResponse::bookPrice)
			.orElseThrow(() -> new IllegalArgumentException("bookPrice is null"));
		return userAndCouponFeignClient.findCouponByOrder(
			Collections.singletonList(bookId),
			categoryId,
			bookPrice
		).getBody();
	}



	@Override
	public UserAndCouponOrderResponseDTO getSelectedCouponByOrder(Long couponId) {

		return userAndCouponFeignClient.getSelectedCoupon(couponId).getBody();

	}

}


