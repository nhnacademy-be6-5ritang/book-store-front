package com.nhnacademy.bookstorefront.userandcoupon.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookByOrderCouponResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetListWrappingResponse;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.NoCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.OneCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponOrderResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;

/**
 * @author 이기훈
 * 사용자와 쿠폰 서비스 인터페이스
 */
public interface UserAndCouponService {
	/**
	 * 사용자를 위한 쿠폰을 생성합니다.
	 *
	 * @param couponId 생성할 쿠폰의 ID
	 */
	void createUserAndCoupon(Long couponId);

	/**
	 * 신규 사용자에게 환영 쿠폰을 생성합니다.
	 *
	 * @param userId 환영 쿠폰을 받을 사용자의 ID
	 */
	void createWelcomeCoupon(Long userId);

	/**
	 * 사용자와 쿠폰 목록을 페이징하여 반환합니다.
	 *
	 * @param userId 사용자 ID
	 * @param type 쿠폰 유형
	 * @param pageable 페이징 정보
	 * @return 사용자와 쿠폰 페이징 목록
	 */
	Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(Long userId, String type, Pageable pageable);

	/**
	 * 사용자와 쿠폰 목록을 페이징하여 반환합니다.
	 *
	 * @param pageable 페이징 정보
	 * @return 사용자와 쿠폰 페이징 목록
	 */
	Page<UserAndCouponResponseDTO> getUserAndCouponByIdPaging(Pageable pageable);

	/**
	 * 주문 목록 ID를 기반으로 모든 사용자와 쿠폰을 반환합니다.
	 *
	 * @param orderListId 주문 목록 ID
	 * @return 사용자와 쿠폰 목록
	 */
	List<UserAndCouponResponseDTO> getAllUserAndCouponByOrder(Long orderListId);

	/**
	 * 장바구니 주문의 모든 사용자와 쿠폰을 반환합니다.
	 *
	 * @param bookDetails 책 주문 쿠폰 응답 목록
	 * @return 사용자와 쿠폰 목록
	 */
	List<UserAndCouponResponseDTO> getAllUserAndCouponByCartOrder(List<GetBookByOrderCouponResponse> bookDetails);

	/**
	 * 주문에 선택된 쿠폰을 반환합니다.
	 *
	 * @param couponId 선택된 쿠폰 ID
	 * @return 사용자와 쿠폰 주문 응답 DTO
	 */
	UserAndCouponOrderResponseDTO getSelectedCouponByOrder(Long couponId);

	/**
	 * 사용자가 실제 사용자인지 확인합니다.
	 *
	 * @return 실제 사용자 여부
	 */
	Boolean isRealUserCheck();

	/**
	 * 한 개의 쿠폰을 반환하는 모델을 생성합니다.
	 *
	 * @param userAndCouponOrderResponseDTO 사용자와 쿠폰 주문 응답 DTO
	 * @param orderResponse 주문 응답
	 * @param deliveryPolicyResponse 배송 정책 응답
	 * @param wrappingResponse 포장 응답
	 * @return 한 개의 쿠폰 응답 DTO
	 */
	OneCouponResponseDTO oneCouponReturnModel(UserAndCouponOrderResponseDTO userAndCouponOrderResponseDTO,
		GetBookOrderResponse orderResponse,
		GetDeliveryPolicyResponse deliveryPolicyResponse,
		GetListWrappingResponse wrappingResponse);

	/**
	 * 쿠폰 없이 반환하는 모델을 생성합니다.
	 *
	 * @param orderResponse 주문 응답
	 * @param deliveryPolicyResponse 배송 정책 응답
	 * @param wrappingResponse 포장 응답
	 * @return 쿠폰 없는 응답 DTO
	 */
	NoCouponResponseDTO noCouponReturnModel(GetBookOrderResponse orderResponse,
		GetDeliveryPolicyResponse deliveryPolicyResponse,
		GetListWrappingResponse wrappingResponse);

	/**
	 * 장바구니의 쿠폰 없이 반환하는 모델을 생성합니다.
	 *
	 * @param orderPrice 주문 가격
	 * @param deliveryPrice 배송 가격
	 * @param wrappingTotalPrice 포장 총 가격
	 * @return 쿠폰 없는 응답 DTO
	 */
	NoCouponResponseDTO noCouponReturnModelCart(BigDecimal orderPrice, BigDecimal deliveryPrice,
		BigDecimal wrappingTotalPrice);

	/**
	 * 장바구니의 한 개의 쿠폰을 반환하는 모델을 생성합니다.
	 *
	 * @param userAndCouponOrderResponseDTO 사용자와 쿠폰 주문 응답 DTO
	 * @param orderPrice 주문 가격
	 * @param deliveryPrice 배송 가격
	 * @param wrappingTotalPrice 포장 총 가격
	 * @return 한 개의 쿠폰 응답 DTO
	 */
	OneCouponResponseDTO oneCouponReturnModelCart(UserAndCouponOrderResponseDTO userAndCouponOrderResponseDTO,
		BigDecimal orderPrice, BigDecimal deliveryPrice,
		BigDecimal wrappingTotalPrice);

	/**
	 * 결제 후 쿠폰을 업데이트합니다.
	 *
	 * @param couponId 업데이트할 쿠폰의 ID
	 */
	void updateCouponAfterPayment(Long couponId);

	/**
	 * 책 세부 사항에 따라 장바구니 주문 쿠폰을 반환합니다.
	 *
	 * @param orderListId 주문 목록 ID
	 * @return 책 주문 쿠폰 응답
	 */
	GetBookByOrderCouponResponse getCartOrderCouponByBookDetails(Long orderListId);
}
