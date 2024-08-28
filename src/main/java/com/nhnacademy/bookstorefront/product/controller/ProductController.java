package com.nhnacademy.bookstorefront.product.controller;

import com.nhnacademy.bookstorefront.cache.service.CacheService;
import com.nhnacademy.bookstorefront.global.util.PagingModel;
import com.nhnacademy.bookstorefront.product.dto.response.GetProductSimpleResponse;
import com.nhnacademy.bookstorefront.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 김기욱, 이경헌
 * 상품 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final CacheService cacheService;
    private static final String REDIRECT_URL = "redirect:/api/books/page";

    /**
     * 메인 페이지의 상품을 조회합니다.
     *
     * @param model 모델 객체
     * @return 메인 페이지 뷰 이름
     */
    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("bestSellerProductsCache", cacheService.getBestSellerBooks());
        model.addAttribute("likesProductsCache", cacheService.getLikesBooks());
        model.addAttribute("newestProductsCache", cacheService.getNewestBooks());
        return "index";
    }

    @GetMapping("/category")
    public String getBooksByCategoryName(
            @PageableDefault(page = 1, size = 12, sort = {
                    "bookPublishDate"}, direction = Sort.Direction.DESC) Pageable defaultPageable,
            @RequestParam(defaultValue = "bookPublishDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam String categoryName, Model model) {

        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(defaultPageable.getPageNumber(), defaultPageable.getPageSize(), sort);

        Page<GetProductSimpleResponse> products = productService.getBooksByCategory(pageable, categoryName);
        model.addAttribute("products", products);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        PagingModel.pagingProcessing(pageable, model, products,
                "/api/products/category?categoryName=" + categoryName + "&direction=" + direction + "&sortBy=" + sortBy, 5);

        return "product/list-product-by-category";
    }


}
