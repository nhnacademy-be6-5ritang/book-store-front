package com.nhnacademy.bookstorefront.userandcoupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request.UserAndCouponRequestUpdateDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;

public interface UserAndCouponService {


    UserAndCouponResponseDTO createUserAndCoupon(Long couponId);

    UserAndCouponResponseDTO updateUserAndCoupon(Long userId, UserAndCouponRequestUpdateDTO requestDTO);


    // Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(Pageable pageable);
    Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(Long userId, String type,Pageable pageable);
    Page<UserAndCouponResponseDTO> getUserAndCouponByIdPaging(Long userId, Pageable pageable);

}
