package com.nhnacademy.bookstorefront.userandcoupon.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import org.springframework.http.ResponseEntity;


import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookByOrderCouponResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetListWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetWrappingResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.NoCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.OneCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponOrderResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.feignclient.UserAndCouponFeignClient;
import com.nhnacademy.bookstorefront.userandcoupon.service.UserAndCouponService;

@Service
public class UserAndCouponServiceImpl implements UserAndCouponService {

	private final UserAndCouponFeignClient userAndCouponFeignClient;
	private final OrderServiceClient orderServiceClient;

	public UserAndCouponServiceImpl(UserAndCouponFeignClient userAndCouponFeignClient,
		OrderServiceClient orderServiceClient) {
		this.userAndCouponFeignClient = userAndCouponFeignClient;
		this.orderServiceClient = orderServiceClient;
	}

	@Override
	public void createUserAndCoupon(Long couponTemplateId) {

		userAndCouponFeignClient.createUserAndCoupon(couponTemplateId);

	}

	@Override
	public void createWelcomeCoupon(Long userId) {

		userAndCouponFeignClient.createUserWelcomeCouponIssue(userId);

	}

	@Override
	public Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(Long userId, String type, Pageable pageable) {
		return userAndCouponFeignClient.getAllUsersAndCouponsByManagerPaging(userId, type, pageable).getBody();
	}

	@Override
	public Page<UserAndCouponResponseDTO> getUserAndCouponByIdPaging(Pageable pageable) {
		return userAndCouponFeignClient.getAllUserAndCouponsByUserPaging(pageable).getBody();

	}

	@Override
	public List<UserAndCouponResponseDTO> getAllUserAndCouponByOrder(Long orderListId) {
		GetBookByOrderCouponResponse response = orderServiceClient.getBookByOneOrder(orderListId).getBody();
		Long bookId = Optional.ofNullable(response)
			.map(GetBookByOrderCouponResponse::bookId)
			.orElseThrow(() -> new IllegalArgumentException("bookId is null"));
		List<Long> categoryId = Optional.of(response)
			.map(GetBookByOrderCouponResponse::categoryId)
			.orElseThrow(() -> new IllegalArgumentException("categoryId is null"));
		BigDecimal bookPrice= Optional.of(response)
			.map(GetBookByOrderCouponResponse::bookPrice)
			.orElseThrow(() -> new IllegalArgumentException("bookPrice is null"));
		return userAndCouponFeignClient.findCouponByOrder(
			Collections.singletonList(bookId),
			categoryId,
			bookPrice
		).getBody();
	}

	@Override
	public List<UserAndCouponResponseDTO> getAllUserAndCouponByCartOrder(List<GetBookByOrderCouponResponse> bookDetails){
		return userAndCouponFeignClient.findCouponByCartOrder(bookDetails).getBody();
	}


	@Override
	public GetBookByOrderCouponResponse getCartOrderCouponByBookDetails(Long orderListId) {
		// orderServiceClient.getBookByOneOrder(orderListId) 호출 결과가 null일 수 있으므로 안전하게 처리

		return Optional.ofNullable(orderServiceClient.getBookByOneOrder(orderListId))
			.map(ResponseEntity::getBody)
			.orElseThrow(() -> new IllegalArgumentException("Response is null"));
	}



	@Override
	public UserAndCouponOrderResponseDTO getSelectedCouponByOrder(Long couponId) {

		return userAndCouponFeignClient.getSelectedCoupon(couponId).getBody();

	}



	@Override
	public OneCouponResponseDTO oneCouponReturnModel(UserAndCouponOrderResponseDTO userAndCouponOrderResponseDTO, GetBookOrderResponse orderResponse, GetDeliveryPolicyResponse deliveryPolicyResponse, GetListWrappingResponse wrappingResponse) {
		// 정액쿠폰인지 체크
		BigDecimal salePrice = userAndCouponOrderResponseDTO.salePrice();
		BigDecimal saleRate = userAndCouponOrderResponseDTO.saleRate();
		BigDecimal maxSalePrice = userAndCouponOrderResponseDTO.maxSalePrice();
		BigDecimal orderPrice= orderResponse.getBookResponse().bookPrice().multiply(BigDecimal.valueOf(orderResponse.quantity()));


		BigDecimal wrappingTotalPrice= BigDecimal.ZERO;

		for (GetWrappingResponse response : wrappingResponse.wrapping()) {
			wrappingTotalPrice = wrappingTotalPrice.add(response.price());
		}






		BigDecimal deliveryPrice = deliveryPolicyResponse.deliveryPolicyPrice();

		// 할인 금액 및 최종 가격 초기화
		BigDecimal discount = BigDecimal.ZERO;
		BigDecimal orderPriceAfterCoupon = orderPrice;




		// 정액 쿠폰 적용
		if (salePrice != null) {
			if (orderPrice.compareTo(salePrice) < 0) {
				discount = orderPrice;
			} else {
				discount = salePrice;
			}
			orderPriceAfterCoupon = orderPrice.subtract(discount);
		}
		// 정률 쿠폰 적용
		else if (saleRate != null && maxSalePrice != null) {
			BigDecimal calculatedDiscount = orderPrice.multiply(saleRate);
			if (calculatedDiscount.compareTo(maxSalePrice) > 0) {
				calculatedDiscount = maxSalePrice;
			}
			if (calculatedDiscount.compareTo(orderPrice) > 0) {
				discount = orderPrice;
			} else {
				discount = calculatedDiscount;
			}
			orderPriceAfterCoupon = orderPrice.subtract(discount);
		}

		// 최종가격
		BigDecimal	orderPriceBeforePoint= orderPriceAfterCoupon.add(wrappingTotalPrice).add(deliveryPrice);

		// DTO 생성 및 반환
		return new OneCouponResponseDTO(
			orderPrice,
			discount,
			orderPriceAfterCoupon,
			orderPriceBeforePoint
		);


	}

	@Override
	public OneCouponResponseDTO oneCouponReturnModelCart(UserAndCouponOrderResponseDTO userAndCouponOrderResponseDTO,BigDecimal orderPrice,BigDecimal deliveryPrice, BigDecimal wrappingTotalPrice) {
		// 정액쿠폰인지 체크
		BigDecimal salePrice = userAndCouponOrderResponseDTO.salePrice();
		BigDecimal saleRate = userAndCouponOrderResponseDTO.saleRate();
		BigDecimal maxSalePrice = userAndCouponOrderResponseDTO.maxSalePrice();


		// 할인 금액 및 최종 가격 초기화
		BigDecimal discount = BigDecimal.ZERO;
		BigDecimal orderPriceAfterCoupon = orderPrice;




		// 정액 쿠폰 적용
		if (salePrice != null) {
			if (orderPrice.compareTo(salePrice) < 0) {
				discount = orderPrice;
			} else {
				discount = salePrice;
			}
			orderPriceAfterCoupon = orderPrice.subtract(discount);
		}
		// 정률 쿠폰 적용
		else if (saleRate != null && maxSalePrice != null) {
			BigDecimal calculatedDiscount = orderPrice.multiply(saleRate);
			if (calculatedDiscount.compareTo(maxSalePrice) > 0) {
				calculatedDiscount = maxSalePrice;
			}
			if (calculatedDiscount.compareTo(orderPrice) > 0) {
				discount = orderPrice;
			} else {
				discount = calculatedDiscount;
			}
			orderPriceAfterCoupon = orderPrice.subtract(discount);
		}

		// 최종가격
		BigDecimal	orderPriceBeforePoint= orderPriceAfterCoupon.add(wrappingTotalPrice).add(deliveryPrice);

		// DTO 생성 및 반환
		return new OneCouponResponseDTO(
			orderPrice,
			discount,
			orderPriceAfterCoupon,
			orderPriceBeforePoint
		);


	}



	@Override
	public NoCouponResponseDTO noCouponReturnModel(GetBookOrderResponse orderResponse, GetDeliveryPolicyResponse deliveryPolicyResponse, GetListWrappingResponse wrappingResponse) {
		// 정액쿠폰인지 체크

		BigDecimal orderPrice= orderResponse.getBookResponse().bookPrice().multiply(BigDecimal.valueOf(orderResponse.quantity()));



		BigDecimal wrappingTotalPrice= BigDecimal.ZERO;

		for (GetWrappingResponse response : wrappingResponse.wrapping()) {
			wrappingTotalPrice = wrappingTotalPrice.add(response.price());
		}



		BigDecimal deliveryPrice = deliveryPolicyResponse.deliveryPolicyPrice();

		// 할인 금액 및 최종 가격 초기화
		BigDecimal discount = BigDecimal.ZERO;



		// 최종가격
		BigDecimal	orderPriceBeforePoint= orderPrice.add(wrappingTotalPrice).add(deliveryPrice);

		// DTO 생성 및 반환
		return new NoCouponResponseDTO(
			orderPrice,
			discount,
			orderPriceBeforePoint
		);


	}

	@Override
	public NoCouponResponseDTO noCouponReturnModelCart(BigDecimal orderPrice,BigDecimal deliveryPrice, BigDecimal wrappingTotalPrice) {
		// 할인 금액 및 최종 가격 초기화
		BigDecimal discount = BigDecimal.ZERO;

		// 최종가격
		BigDecimal	orderPriceBeforePoint= orderPrice.add(wrappingTotalPrice).add(deliveryPrice);

		// DTO 생성 및 반환
		return new NoCouponResponseDTO(
			orderPrice,
			discount,
			orderPriceBeforePoint
		);


	}



	@Override
	public Boolean isRealUserCheck() {

		return userAndCouponFeignClient.isRealUserCheck().getBody();

	}


	@Override
	public void updateCouponAfterPayment(Long couponId){

		userAndCouponFeignClient.updateCouponAfterPayment(couponId);
	}




}


