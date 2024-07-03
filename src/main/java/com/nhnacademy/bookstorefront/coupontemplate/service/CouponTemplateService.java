package com.nhnacademy.bookstorefront.coupontemplate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.request.CouponTemplateCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.response.CouponTemplateResponseDTO;

public interface CouponTemplateService {

    void createCouponTemplate(CouponTemplateCreateRequestDTO requestDTO);
    Page<CouponTemplateResponseDTO> getAllCouponTemplatesByManagerPaging(Pageable pageable);

    Page<CouponTemplateResponseDTO> getAllCouponTemplatesByUserPaging(Pageable pageable);
}