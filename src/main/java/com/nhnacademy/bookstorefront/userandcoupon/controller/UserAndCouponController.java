package com.nhnacademy.bookstorefront.userandcoupon.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhnacademy.bookstorefront.global.util.PagingModel;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookByOrderCouponResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.service.Impl.BookOrderServiceImpl;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.service.UserAndCouponService;

@Controller
@RequestMapping("/coupons")
public class UserAndCouponController {

    private final UserAndCouponService userAndCouponService;
    private final BookOrderServiceImpl bookOrderServiceImpl;

    public UserAndCouponController(UserAndCouponService userAndCouponService , BookOrderServiceImpl bookOrderServiceImpl) {
        this.userAndCouponService = userAndCouponService;
        this.bookOrderServiceImpl = bookOrderServiceImpl;
    }

    @PostMapping("/{couponId}")
    public String createUserAndCoupon(@PathVariable("couponId") Long couponTemplateId) {
        try{
        userAndCouponService.createUserAndCoupon(couponTemplateId);
            String message = URLEncoder.encode("쿠폰 발급 완료", StandardCharsets.UTF_8);
        return "redirect:/coupons/issue?message="+message;

        } catch (Exception e){
            return "redirect:/auth/login";
        }
    }


    // 쿠폰 목록을 가져오기
    @GetMapping("/orders/{orderListId}/users/{deliveryId}")
    public String getOrderCoupon(@PathVariable("orderListId") Long orderListId, @PathVariable("deliveryId") Long deliveryId, Model model) {
        // Currentuser 비회원처리
        if(!userAndCouponService.isRealUserCheck()){
            return "redirect:/api/orders/createOrderTest/" + orderListId + "/" + deliveryId;
        }


        List<UserAndCouponResponseDTO> couponList = userAndCouponService.getAllUserAndCouponByOrder(orderListId);

        model.addAttribute("orderListId", orderListId);
        model.addAttribute("deliveryId", deliveryId);
        model.addAttribute("couponList", couponList);


        return "coupon-user/order-use-coupon";
    }

    @PostMapping("/orders/{orderListId}/users/{deliveryId}")
    public String selectCouponByOrder(@PathVariable("orderListId") Long orderListId, @PathVariable("deliveryId") Long deliveryId,
        @RequestParam(value = "couponId", required = false) Long couponId,
        RedirectAttributes redirectAttributes) {


        redirectAttributes.addAttribute("couponId", couponId);


        return "redirect:/api/orders/createOrderTest/" + orderListId + "/" + deliveryId;
    }

    // 카트 주문 쿠폰 목록을 가져오기
    @GetMapping("/orders/users/{deliveryId}/{orderInfoId}")
    public String getCartOrderCoupon(@PathVariable("deliveryId") Long deliveryId, @PathVariable String orderInfoId, Model model) {
        List<GetBookOrderResponse> bookOrders = bookOrderServiceImpl.getBookOrderByOrderId(orderInfoId);
		// Currentuser 비회원처리
		if (!userAndCouponService.isRealUserCheck()) {
			return "redirect:/api/orders/createOrderTest/" + deliveryId + "/cart/" + orderInfoId;
		}

		List<GetBookByOrderCouponResponse> bookDetails = new ArrayList<>(List.of());
		for (GetBookOrderResponse bookOrder : bookOrders) {
            GetBookByOrderCouponResponse bookByOrderCouponResponse = userAndCouponService.getCartOrderCouponByBookDetails(bookOrder.orderListId());
            bookDetails.add(bookByOrderCouponResponse);
		}

        List<UserAndCouponResponseDTO> couponList = userAndCouponService.getAllUserAndCouponByCartOrder(bookDetails);






		model.addAttribute("deliveryId", deliveryId);
		model.addAttribute("couponList", couponList);
        model.addAttribute("orderInfoId", orderInfoId);

		return "coupon-user/cart-order-use-coupon";
	}


    // 선택한 쿠폰목록 번호를 가져오기
    @PostMapping("/orders/users/{deliveryId}/cart/{orderInfoId}")
    public String selectCouponByOrder(@PathVariable("deliveryId") Long deliveryId,
        @RequestParam(value = "couponId", required = false) Long couponId,
      RedirectAttributes redirectAttributes, @PathVariable String orderInfoId) {


        redirectAttributes.addAttribute("couponId", couponId);

        return "redirect:/api/orders/createOrderTest/" + deliveryId + "/cart/" + orderInfoId;
    }



    @GetMapping("/users/user")
    public String getUserAndCouponByIdPaging( @PageableDefault(page = 1, size = 4)Pageable pageable,Model model) {
        Page<UserAndCouponResponseDTO> userAndCoupon = userAndCouponService.getUserAndCouponByIdPaging(pageable);


        PagingModel.pagingProcessing(pageable, model, userAndCoupon, "/coupons/users/user", 5);


        model.addAttribute("userAndCoupon", userAndCoupon);
        return "coupon-user/mypage-coupon";
    }





    @GetMapping("/users")
    public String getAllUserAndCouponPaging(
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) String type,
        @PageableDefault(page = 1, size = 4)Pageable pageable,Model model) {
        Page<UserAndCouponResponseDTO> userAndCoupon = userAndCouponService.getAllUserAndCouponPaging(userId, type, pageable);




        int blockLimit = 3;
        int startPage = 1; // 시작 페이지 기본값 설정
        int endPage = 1; // 끝 페이지 기본값 설정

        if (!userAndCoupon.isEmpty()) {
            // 검색 결과가 있는 경우에만 페이지 번호 계산
            int adjustedPage = Math.max(pageable.getPageNumber(), 1);
            startPage = (((int)(Math.ceil((double)adjustedPage / blockLimit))) - 1) * blockLimit + 1;
            endPage = Math.min((startPage + blockLimit - 1), userAndCoupon.getTotalPages());
        }


        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("userId", userId == null ? "" : userId);
        searchParams.put("type", type == null ? "" : type);



        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("userAndCoupon", userAndCoupon);
        model.addAttribute("param", searchParams);
        return "coupon-manager/coupon-issued";
    }


}