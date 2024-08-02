package com.nhnacademy.bookstorefront.couponpolicy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyCreateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyUpdateRequestDTO;
import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.response.CouponPolicyResponseDTO;

/**
 * @author 이기훈
 * 쿠폰 정책에 대한 서비스 인터페이스입니다.
 */
public interface CouponPolicyService {

	/**
	 * 환영 쿠폰 정책을 발급합니다.
	 *
	 * @param requestDTO 환영 쿠폰 정책 생성에 필요한 데이터가 포함된 DTO 객체입니다.
	 */
	void issueWelcomeCoupon(CouponPolicyCreateRequestDTO requestDTO);

	/**
	 * 생일 쿠폰 정책을 발급합니다.
	 *
	 * @param requestDTO 생일 쿠폰 정책 생성에 필요한 데이터가 포함된 DTO 객체입니다.
	 */
	void issueBirthdayCoupon(CouponPolicyCreateRequestDTO requestDTO);

	/**
	 * 도서 쿠폰 정책을 발급합니다.
	 *
	 * @param requestDTO 도서 쿠폰 정책 생성에 필요한 데이터가 포함된 DTO 객체입니다.
	 */
	void issueBookCoupon(CouponPolicyCreateRequestDTO requestDTO);

	/**
	 * 카테고리 쿠폰 정책을 발급합니다.
	 *
	 * @param requestDTO 카테고리 쿠폰 정책 생성에 필요한 데이터가 포함된 DTO 객체입니다.
	 */
	void issueCategoryCoupon(CouponPolicyCreateRequestDTO requestDTO);

	/**
	 * 세일 쿠폰 정책을 발급합니다.
	 *
	 * @param requestDTO 세일 쿠폰 정책 생성에 필요한 데이터가 포함된 DTO 객체입니다.
	 */
	void issueSaleCoupon(CouponPolicyCreateRequestDTO requestDTO);

	/**
	 * 모든 쿠폰 정책을 페이지네이션을 적용하여 조회합니다.
	 *
	 * @param pageable 페이지네이션 정보를 포함하는 객체입니다.
	 * @return {@link Page} 객체로, {@link CouponPolicyResponseDTO} 타입의 쿠폰 정책 정보를 포함합니다.
	 */
	Page<CouponPolicyResponseDTO> getAllCouponPolicies(Pageable pageable);

	/**
	 * 특정 쿠폰 정책을 업데이트합니다.
	 *
	 * @param id 쿠폰 정책의 고유 식별자입니다.
	 * @param requestDTO 쿠폰 정책 업데이트에 필요한 데이터가 포함된 DTO 객체입니다.
	 */
	void updateCouponPolicy(Long id, CouponPolicyUpdateRequestDTO requestDTO);
}
