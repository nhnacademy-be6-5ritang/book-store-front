package com.nhnacademy.bookstorefront.userandcoupon.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponOrderResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;

public interface UserAndCouponService {


    void createUserAndCoupon(Long couponId);

    void createWelcomeCoupon(Long userId);

    Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(Long userId, String type,Pageable pageable);
    Page<UserAndCouponResponseDTO> getUserAndCouponByIdPaging(Pageable pageable);

    List<UserAndCouponResponseDTO> getAllUserAndCouponByOrder(Long orderListId);
    UserAndCouponOrderResponseDTO getSelectedCouponByOrder(Long couponId);
    Boolean isRealUserCheck();
}
