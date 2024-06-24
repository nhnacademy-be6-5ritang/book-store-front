package com.nhnacademy.bookstorefront.userandcoupon.service;

import java.util.List;

import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request.UserAndCouponRequestUpdateDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;

public interface UserAndCouponService {


    UserAndCouponResponseDTO createUserAndCoupon(Long couponId);

    UserAndCouponResponseDTO updateUserAndCoupon(String userEmail, UserAndCouponRequestUpdateDTO requestDTO);

    List<UserAndCouponResponseDTO> getAllUserAndCoupons();

    List<UserAndCouponResponseDTO> getUserAndCouponById(String userEmail);

}
