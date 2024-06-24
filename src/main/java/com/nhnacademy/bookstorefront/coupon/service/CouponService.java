package com.nhnacademy.bookstorefront.coupon.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.coupon.domain.dto.request.CouponCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupon.domain.dto.response.CouponResponseDTO;

public interface CouponService {

    CouponResponseDTO createCoupon(CouponCreateRequestDTO requestDTO);
    List<CouponResponseDTO> getAllCoupons();
    // CouponResponseDTO getCouponById(Long id);

    Page<CouponResponseDTO> getAllCouponsPaging(Pageable pageable);
}