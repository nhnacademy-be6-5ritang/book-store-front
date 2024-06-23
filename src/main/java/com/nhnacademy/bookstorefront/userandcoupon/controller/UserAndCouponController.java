package com.nhnacademy.bookstorefront.userandcoupon.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.service.UserAndCouponService;

@Controller
@RequestMapping("/coupons")
public class UserAndCouponController {

    private final UserAndCouponService userAndCouponService;

    public UserAndCouponController(UserAndCouponService userAndCouponService) {
        this.userAndCouponService = userAndCouponService;
    }

    // TODO : 서비스에서 유저아이디  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //  이걸로 가져오기. 지금은 임시방편으로 아이디 1이라고 하고 하기
    @PostMapping("/{couponId}")
    public String createUserAndCoupon(@PathVariable("couponId") Long couponId) {
        UserAndCouponResponseDTO responseDTO = userAndCouponService.createUserAndCoupon(couponId);
        return "redirect:/coupons/issue";
    }


    // TODO 1 : 쿠폰의 상태를 사용됨으로 바꿈
    //   결제시 사용할경우
    //
    // @PatchMapping("/payment/users/{userId}")
    // public String updateUserAndCouponUsed(@PathVariable("userId") String userId, @ModelAttribute UserAndCouponRequestUpdateDTO requestDTO) {
    //     UserAndCouponResponseDTO responseDTO = userAndCouponService.updateUserAndCoupon(userId, requestDTO);
    //     return "#";
    // }

    // TODO 2 : 쿠폰의 상태를 사용됨으로 바꿈
    //   로그인시 기간만료체크
    //
    // @PatchMapping("/auth/users/{userId}")
    // public String updateUserAndCouponExpired(@PathVariable("userId") String userId, @ModelAttribute UserAndCouponRequestUpdateDTO requestDTO) {
    //     UserAndCouponResponseDTO responseDTO = userAndCouponService.updateUserAndCoupon(userId, requestDTO);
    //     return "#";
    // }

    // TODO 3 : 마이페이지 쿠폰목록 보여주기 (페이징처리)
    @GetMapping("/users/{userId}")
    public String getUserAndCouponById( @PathVariable("userId") String userId, Model model) {
        List<UserAndCouponResponseDTO> userAndCoupon = userAndCouponService.getUserAndCouponById(userId);
        model.addAttribute("userAndCoupon", userAndCoupon);
        return "mypage-coupon";
    }

    // TODO 4 : 판매자페이지 유저가 발급한 쿠폰목록 보여주기 (페이징처리)
    @GetMapping("/users")
    public String getAllUserAndCoupon() {
        List<UserAndCouponResponseDTO> userAndCoupon = userAndCouponService.getAllUserAndCoupons();
        return "coupon-issued";
    }


}