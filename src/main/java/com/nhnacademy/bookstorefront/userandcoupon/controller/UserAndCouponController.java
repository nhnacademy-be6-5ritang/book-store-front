package com.nhnacademy.bookstorefront.userandcoupon.controller;

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

import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.service.UserAndCouponService;

@Controller
@RequestMapping("/coupons")
public class UserAndCouponController {

    private final UserAndCouponService userAndCouponService;

    public UserAndCouponController(UserAndCouponService userAndCouponService) {
        this.userAndCouponService = userAndCouponService;
    }

    @PostMapping("/{couponId}")
    public String createUserAndCoupon(@PathVariable("couponId") Long couponTemplateId) {
        userAndCouponService.createUserAndCoupon(couponTemplateId);
        return "redirect:/coupons/issue";
    }


    // 쿠폰 목록을 가져오기
    @GetMapping("/orders/{orderListId}/users/{deliveryId}")
    public String getOrderCoupon(@PathVariable("orderListId") Long orderListId, @PathVariable("deliveryId") Long deliveryId, Model model) {
        List<UserAndCouponResponseDTO> couponList = userAndCouponService.getAllUserAndCouponByOrder(orderListId);

        model.addAttribute("orderListId", orderListId);
        model.addAttribute("deliveryId", deliveryId);
        model.addAttribute("couponList", couponList);


        return "coupon-user/order-use-coupon";
    }


    // 선택한 쿠폰목록 번호를 가져오기
    @PostMapping("/orders/{orderListId}/users/{deliveryId}")
    public String selectCouponByOrder(@PathVariable("orderListId") Long orderListId, @PathVariable("deliveryId") Long deliveryId,
        @RequestParam(value = "couponId", required = false) Long couponId,
      RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("couponId", couponId);


        return "redirect:/api/orders/createOrderTest/" + orderListId + "/" + deliveryId;
    }



    @GetMapping("/users/user")
    public String getUserAndCouponByIdPaging( @PageableDefault(page = 1, size = 3)Pageable pageable,Model model) {
        Page<UserAndCouponResponseDTO> userAndCoupon = userAndCouponService.getUserAndCouponByIdPaging(pageable);

        int blockLimit = 3;
        int startPage = 1; // 1 4 7 10 ~~
        int endPage = 1;


        if (!userAndCoupon.isEmpty()) {
            // 검색 결과가 있는 경우에만 페이지 번호 계산
            int adjustedPage = Math.max(pageable.getPageNumber(), 1);
            startPage = (((int)(Math.ceil((double)adjustedPage / blockLimit))) - 1) * blockLimit + 1;
            endPage = Math.min((startPage + blockLimit - 1), userAndCoupon.getTotalPages());
        }



        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("userAndCoupon", userAndCoupon);
        return "coupon-user/mypage-coupon";
    }





    @GetMapping("/users")
    public String getAllUserAndCouponPaging(
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) String type,
        @PageableDefault(page = 1, size = 3)Pageable pageable,Model model) {
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