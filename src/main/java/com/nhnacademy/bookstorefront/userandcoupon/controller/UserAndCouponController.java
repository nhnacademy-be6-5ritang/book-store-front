package com.nhnacademy.bookstorefront.userandcoupon.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    // @GetMapping("/users/{userEmail}")
    // public String getUserAndCouponById( @PathVariable("userEmail") String userEmail, Model model) {
    //     List<UserAndCouponResponseDTO> userAndCoupon = userAndCouponService.getUserAndCouponById(userEmail);
    //     model.addAttribute("userAndCoupon", userAndCoupon);
    //     return "mypage-coupon";
    // }

    @GetMapping("/users/{userEmail}")
    public String getUserAndCouponByIdPaging( @PathVariable("userEmail") String userEmail, @PageableDefault(page = 1)Pageable pageable,Model model) {
        Page<UserAndCouponResponseDTO> userAndCoupon = userAndCouponService.getUserAndCouponByIdPaging(userEmail, pageable);

        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = Math.min((startPage + blockLimit - 1), userAndCoupon.getTotalPages());

        userAndCoupon.forEach(userAndCouponDTO -> model.addAttribute("user", userAndCouponDTO));
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("userAndCoupon", userAndCoupon);
        return "mypage-coupon";
    }


    // @GetMapping("/users")
    // public String getAllUserAndCoupon(Model model) {
    //     List<UserAndCouponResponseDTO> userAndCoupon = userAndCouponService.getAllUserAndCoupons();
    //     model.addAttribute("userAndCoupon", userAndCoupon);
    //     return "coupon-issued";
    // }

    // 판매자가 쿠폰목록 확인할 수 있는 페이지,  페이징 처리
    @GetMapping("/users")
    public String getAllUserAndCouponPaging(@PageableDefault(page = 1)Pageable pageable,Model model) {
        Page<UserAndCouponResponseDTO> userAndCoupon = userAndCouponService.getAllUserAndCouponPaging(pageable);

        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = Math.min((startPage + blockLimit - 1), userAndCoupon.getTotalPages());


        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("userAndCoupon", userAndCoupon);
        return "coupon-issued";
    }


}