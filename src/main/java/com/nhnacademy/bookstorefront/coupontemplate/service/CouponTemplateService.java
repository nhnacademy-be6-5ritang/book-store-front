package com.nhnacademy.bookstorefront.coupontemplate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.request.CouponTemplateCreateRequestDTO;
import com.nhnacademy.bookstorefront.coupontemplate.domain.dto.response.CouponTemplateResponseDTO;

/**
 * @author 이기훈
 * 쿠폰 템플릿에 대한 서비스 인터페이스입니다.
 */
public interface CouponTemplateService {

	/**
	 * 새로운 쿠폰 템플릿을 생성합니다.
	 *
	 * @param requestDTO 쿠폰 템플릿 생성에 필요한 데이터가 포함된 DTO 객체입니다.
	 */
	void createCouponTemplate(CouponTemplateCreateRequestDTO requestDTO);

	/**
	 * 관리자에 대한 페이지네이션된 쿠폰 템플릿 목록을 조회합니다.
	 *
	 * @param pageable 페이지네이션 정보를 포함하는 객체입니다.
	 * @return {@link Page} 객체로, {@link CouponTemplateResponseDTO} 타입의 쿠폰 템플릿 정보를 포함합니다.
	 */
	Page<CouponTemplateResponseDTO> getAllCouponTemplatesByManagerPaging(Pageable pageable);

	/**
	 * 사용자에 대한 페이지네이션된 쿠폰 템플릿 목록을 조회합니다.
	 *
	 * @param pageable 페이지네이션 정보를 포함하는 객체입니다.
	 * @return {@link Page} 객체로, {@link CouponTemplateResponseDTO} 타입의 쿠폰 템플릿 정보를 포함합니다.
	 */
	Page<CouponTemplateResponseDTO> getAllCouponTemplatesByUserPaging(Pageable pageable);
}
