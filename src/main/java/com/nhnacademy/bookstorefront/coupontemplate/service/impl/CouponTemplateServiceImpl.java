package com.nhnacademy.bookstorefront.coupontemplate.service.impl;

import java.util.List;

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
	public CouponTemplateResponseDTO createCouponTemplate(CouponTemplateCreateRequestDTO requestDTO) {
		return couponTemplateFeignClient.createCouponTemplate(requestDTO).getBody();

	}

	@Override
	public List<CouponTemplateResponseDTO> getAllCouponTemplates() {
		return couponTemplateFeignClient.getAllCouponTemplates().getBody();
	}


	@Override
	public Page<CouponTemplateResponseDTO> getAllCouponTemplatesPaging(Pageable pageable) {
		return couponTemplateFeignClient.getAllCouponTemplatePaging(pageable).getBody();
	}
}