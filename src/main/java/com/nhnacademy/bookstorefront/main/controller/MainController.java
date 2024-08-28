package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.cache.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 이경헌
 * 메인페이지 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
    private final CacheService cacheService;

    /**
     * 메인 페이지의 상품을 조회합니다.
     *
     * @param model 모델 객체
     * @return 메인 페이지 뷰 이름
     */
    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("bestSellerProductsCache", cacheService.getBestSellerBooks());
        model.addAttribute("likesProductsCache", cacheService.getLikesBooks());
        model.addAttribute("newestProductsCache", cacheService.getNewestBooks());
        return "index";
    }

}
