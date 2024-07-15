package com.nhnacademy.bookstorefront.userandcoupon.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetListWrappingResponse;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.NoCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.OneCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponOrderResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;

public interface UserAndCouponService {


    void createUserAndCoupon(Long couponId);

    void createWelcomeCoupon(Long userId);

    Page<UserAndCouponResponseDTO> getAllUserAndCouponPaging(Long userId, String type,Pageable pageable);
    Page<UserAndCouponResponseDTO> getUserAndCouponByIdPaging(Pageable pageable);

    List<UserAndCouponResponseDTO> getAllUserAndCouponByOrder(Long orderListId);
    UserAndCouponOrderResponseDTO getSelectedCouponByOrder(Long couponId);
    Boolean isRealUserCheck();
    OneCouponResponseDTO oneCouponReturnModel(UserAndCouponOrderResponseDTO userAndCouponOrderResponseDTO, GetBookOrderResponse orderResponse, GetDeliveryPolicyResponse deliveryPolicyResponse, GetListWrappingResponse wrappingResponse);
    NoCouponResponseDTO noCouponReturnModel(GetBookOrderResponse orderResponse, GetDeliveryPolicyResponse deliveryPolicyResponse, GetListWrappingResponse wrappingResponse);
}
