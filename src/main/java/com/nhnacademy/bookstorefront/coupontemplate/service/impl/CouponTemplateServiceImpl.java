package com.nhnacademy.bookstorefront.coupontemplate.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.request.CouponTemplateCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.response.CouponTemplateResponseDTO;
import com.nhnacademy.bookstorefront.coupontemplate.feignclient.CouponTemplateFeignClient;
import com.nhnacademy.bookstorefront.coupontemplate.service.CouponTemplateService;

@Service
public class CouponTemplateServiceImpl implements CouponTemplateService {
	private final CouponTemplateFeignClient couponTemplateFeignClient;

	public CouponTemplateServiceImpl(CouponTemplateFeignClient couponTemplateFeignClient) {
		this.couponTemplateFeignClient = couponTemplateFeignClient;
	}

	@Override
	public void createCouponTemplate(CouponTemplateCreateRequestDTO requestDTO) {
		couponTemplateFeignClient.createCouponTemplate(requestDTO);

	}

	@Override
	public Page<CouponTemplateResponseDTO> getAllCouponTemplatesByManagerPaging(Pageable pageable) {
		return couponTemplateFeignClient.getAllCouponTemplatesByManagerPaging(pageable).getBody();
	}


	@Override
	public Page<CouponTemplateResponseDTO> getAllCouponTemplatesByUserPaging(Pageable pageable) {
		return couponTemplateFeignClient.getAllCouponTemplatesByUserPaging(pageable).getBody();
	}
}