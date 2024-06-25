package com.nhnacademy.bookstorefront.userandcoupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request.UserAndCouponRequestUpdateDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;

public interface UserAndCouponService {


    UserAndCouponResponseDTO createUserAndCoupon(Long couponId);

    UserAndCouponResponseDTO updateUserAndCoupon(String userEmail, UserAndCouponRequestUpdateDTO requestDTO);


    // Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(Pageable pageable);
    Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(String userEmail, String type,Pageable pageable);
    Page<UserAndCouponResponseDTO> getUserAndCouponByIdPaging(String userEmail, Pageable pageable);

}
