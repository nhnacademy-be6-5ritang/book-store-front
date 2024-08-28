package com.nhnacademy.bookstorefront.product.service;

import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.product.dto.response.GetProductResponse;
import com.nhnacademy.bookstorefront.product.dto.response.GetProductSimpleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author 김기욱, 이경헌
 * 외부 Book 서비스와 통신하여 책 정보를 관리하는 인터페이스입니다.
 */
public interface ProductService {

    /**
     * 주어진 책 ID에 해당하는 상품을 조회합니다.
     *
     * @param bookId 책 ID
     * @return 책의 상세 정보
     */
    GetProductResponse getProduct(Long bookId);

    /**
     * 모든 책의 상세 정보를 조회합니다.
     *
     * @return 모든 책의 상세 정보 목록
     */
    List<GetProductSimpleResponse> getNewestBooks();

    /**
     * 베스트셀러 책의 목록을 조회합니다.
     *
     * @return 주문된 책 목록이 포함된 {@link List<GetBookDetailResponse>} 객체
     */
    List<GetProductSimpleResponse> getBestSellerBooks();

    /**
     * 사용자가 좋아요를 누른 책의 목록을 조회합니다.
     *
     * @return 좋아요를 누른 책 목록이 포함된 {@link List<GetBookDetailResponse>} 객체
     */
    List<GetProductSimpleResponse> getLikesBooks();

    /**
     * 지정된 카테고리에 해당하는 책 목록을 조회합니다.
     *
     * @param pageable     페이지 정보와 정렬 기준을 포함하는 객체
     * @param categoryName 조회할 책의 카테고리 이름
     * @return 지정된 카테고리에 속하는 책들의 {@link GetProductSimpleResponse} 객체 목록입니다.
     */
    Page<GetProductSimpleResponse> getBooksByCategory(Pageable pageable, String categoryName);
}
