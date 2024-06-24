package com.nhnacademy.bookstorefront.userandcoupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request.UserAndCouponRequestUpdateDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;

public interface UserAndCouponService {


    UserAndCouponResponseDTO createUserAndCoupon(Long couponId);

    UserAndCouponResponseDTO updateUserAndCoupon(String userEmail, UserAndCouponRequestUpdateDTO requestDTO);

    // List<UserAndCouponResponseDTO> getAllUserAndCoupons();

    Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(Pageable pageable);

    // List<UserAndCouponResponseDTO> getUserAndCouponById(String userEmail);
    Page<UserAndCouponResponseDTO> getUserAndCouponByIdPaging(String userEmail, Pageable pageable);

}
