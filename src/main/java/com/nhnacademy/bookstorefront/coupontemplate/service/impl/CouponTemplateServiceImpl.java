package com.nhnacademy.bookstorefront.coupontemplate.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.request.CouponTemplateCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.response.CouponTemplateResponseDTO;
import com.nhnacademy.bookstorefront.coupontemplate.feignclient.CouponTemplateFeignClient;
import com.nhnacademy.bookstorefront.coupontemplate.service.CouponTemplateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponTemplateServiceImpl implements CouponTemplateService {
	private final CouponTemplateFeignClient couponTemplateFeignClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void createCouponTemplate(CouponTemplateCreateRequestDTO requestDTO) {
		couponTemplateFeignClient.createCouponTemplate(requestDTO);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<CouponTemplateResponseDTO> getAllCouponTemplatesByManagerPaging(Pageable pageable) {
		return couponTemplateFeignClient.getAllCouponTemplatesByManagerPaging(pageable).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<CouponTemplateResponseDTO> getAllCouponTemplatesByUserPaging(Pageable pageable) {
		return couponTemplateFeignClient.getAllCouponTemplatesByUserPaging(pageable).getBody();
	}
}
