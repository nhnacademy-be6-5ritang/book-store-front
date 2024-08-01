package com.nhnacademy.bookstorefront.review.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.GetBookOrderWithoutReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;

/**
 * @author 이경헌
 * ReviewService 인터페이스는 리뷰 관리와 관련된 비즈니스 로직을 정의합니다.
 */
public interface ReviewService {
	/**
	 * 페이징된 모든 리뷰 목록을 반환합니다.
	 *
	 * @param pageable 페이징 정보를 포함하는 객체
	 * @return 페이징된 리뷰 응답 페이지
	 */
	Page<GetReviewResponse> getReviews(Pageable pageable);

	/**
	 * 페이징된 모든 사진 리뷰 목록을 반환합니다.
	 *
	 * @param pageable 페이징 정보를 포함하는 객체
	 * @return 페이징된 리뷰 응답 페이지
	 */
	Page<GetReviewResponse> getPhotoReviews(Pageable pageable);

	/**
	 * 페이징된 모든 일반 리뷰 목록을 반환합니다.
	 *
	 * @param pageable 페이징 정보를 포함하는 객체
	 * @return 페이징된 리뷰 응답 페이지
	 */
	Page<GetReviewResponse> getGeneralReviews(Pageable pageable);

	/**
	 * 주어진 책 ID에 해당하는 페이징된 리뷰 목록을 반환합니다.
	 *
	 * @param pageable 페이징 정보를 포함하는 객체
	 * @param bookId   조회할 책의 ID
	 * @return 페이징된 리뷰 응답 페이지
	 */
	Page<GetReviewResponse> getReviewsByBookId(Pageable pageable, Long bookId);

	/**
	 * 주어진 책 ID에 해당하는 일반 리뷰의 페이징된 목록을 반환합니다.
	 *
	 * @param pageable 페이징 정보를 포함하는 객체
	 * @param bookId   조회할 책의 ID
	 * @return 페이징된 일반 리뷰 응답 페이지
	 */
	Page<GetReviewResponse> getGeneralReviewsByBookId(Pageable pageable, Long bookId);

	/**
	 * 주어진 책 ID에 해당하는 사진 리뷰의 페이징된 목록을 반환합니다.
	 *
	 * @param pageable 페이징 정보를 포함하는 객체
	 * @param bookId   조회할 책의 ID
	 * @return 페이징된 사진 리뷰 응답 페이지
	 */
	Page<GetReviewResponse> getPhotoReviewsByBookId(Pageable pageable, Long bookId);

	/**
	 * 현재 사용자에 대한 페이징된 리뷰 목록을 반환합니다.
	 *
	 * @param pageable 페이징 정보를 포함하는 객체
	 * @return 페이징된 리뷰 응답 페이지
	 */
	Page<GetReviewResponse> getReviewsByUserId(Pageable pageable);

	/**
	 * 현재 사용자에 대한 일반 리뷰의 페이징된 목록을 반환합니다.
	 *
	 * @param pageable 페이징 정보를 포함하는 객체
	 * @return 페이징된 일반 리뷰 응답 페이지
	 */
	Page<GetReviewResponse> getGeneralReviewsByUserId(Pageable pageable);

	/**
	 * 현재 사용자에 대한 사진 리뷰의 페이징된 목록을 반환합니다.
	 *
	 * @param pageable 페이징 정보를 포함하는 객체
	 * @return 페이징된 사진 리뷰 응답 페이지
	 */
	Page<GetReviewResponse> getPhotoReviewsByUserId(Pageable pageable);

	/**
	 * 새로운 리뷰를 생성합니다.
	 *
	 * @param request 리뷰 생성 요청 DTO
	 * @param file    리뷰에 첨부할 파일
	 */
	void createReview(CreateReviewRequest request, MultipartFile file);

	/**
	 * 주어진 리뷰 ID에 해당하는 리뷰를 반환합니다.
	 *
	 * @param reviewId 조회할 리뷰의 ID
	 * @return 리뷰 응답 DTO
	 */
	GetReviewResponse getReview(Long reviewId);

	/**
	 * 주어진 리뷰 ID에 해당하는 리뷰를 수정합니다.
	 *
	 * @param request  리뷰 수정 요청 DTO
	 * @param reviewId 수정할 리뷰의 ID
	 */
	void updateReview(UpdateReviewRequest request, Long reviewId, MultipartFile file);

	/**
	 * 주어진 리뷰 ID에 해당하는 리뷰를 삭제합니다.
	 *
	 * @param reviewId 삭제할 리뷰의 ID
	 */
	void deleteReview(Long reviewId);

	/**
	 * 주어진 책 ID에 해당하는 리뷰의 평균 점수를 반환합니다.
	 *
	 * @param bookId 조회할 책의 ID
	 * @return 리뷰의 평균 점수
	 */
	double getReviewsAverageScoreByBookId(Long bookId);

	/**
	 * 리뷰 작성하기 위해 주문 상태가 배송완료된 책 목록을 반환합니다.
	 *
	 * @return 주문 상태가 "배송 완료"인 책의 제목 목록
	 */
	List<GetBookOrderWithoutReviewResponse> getBooksWithoutReviews();
}
