package com.nhnacademy.bookstorefront.userandcoupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;

public interface UserAndCouponService {


    void createUserAndCoupon(Long couponId);

    void createWelcomeCoupon(Long userId);

    Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(Long userId, String type,Pageable pageable);
    Page<UserAndCouponResponseDTO> getUserAndCouponByIdPaging(Pageable pageable);

    // Page<UserAndCouponResponseDTO> getUserAndCouponByIdPaging(Long userId, Pageable pageable);

}
