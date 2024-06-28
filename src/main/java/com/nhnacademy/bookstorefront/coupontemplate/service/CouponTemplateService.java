package com.nhnacademy.bookstorefront.coupontemplate.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.request.CouponTemplateCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.response.CouponTemplateResponseDTO;

public interface CouponTemplateService {

    CouponTemplateResponseDTO createCouponTemplate(CouponTemplateCreateRequestDTO requestDTO);
    List<CouponTemplateResponseDTO> getAllCouponTemplates();
    // CouponResponseDTO getCouponById(Long id);

    Page<CouponTemplateResponseDTO> getAllCouponTemplatesPaging(Pageable pageable);
}