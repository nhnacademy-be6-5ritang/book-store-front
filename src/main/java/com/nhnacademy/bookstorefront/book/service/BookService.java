package com.nhnacademy.bookstorefront.book.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookSearchResult;
import com.nhnacademy.bookstorefront.book.dto.response.CreateBookResponse;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.dto.response.UpdateBookResponse;

/**
 * 외부 Book 서비스와 통신하여 책 정보를 관리하는 인터페이스입니다.
 *
 *
 * @version 1.0
 */
public interface BookService {

	/**
	 * 주어진 책 ID에 해당하는 책의 상세 정보를 조회합니다.
	 *
	 * @param bookId 책 ID
	 * @return 책의 상세 정보
	 */
	GetBookDetailResponse getBook(Long bookId);

	/**
	 * 모든 책의 상세 정보를 조회합니다.
	 *
	 * @return 모든 책의 상세 정보 목록
	 */
	List<GetBookDetailResponse> findAllBooks();

	/**
	 * 모든 책의 상세 정보를 페이지네이션하여 조회합니다.
	 *
	 * @param pageable 페이지 정보
	 * @return 페이지네이션된 책의 상세 정보 목록
	 */
	Page<GetBookDetailResponse> findAllBooks(Pageable pageable);

	/**
	 * 새로운 책을 생성합니다.
	 *
	 * @param request 생성할 책 정보 DTO
	 * @return 생성된 책의 응답 DTO
	 */
	CreateBookResponse createBook(CreateBookRequest request);

	/**
	 * 주어진 책 ID에 해당하는 책을 업데이트합니다.
	 *
	 * @param bookId  업데이트할 책 ID
	 * @param request 업데이트할 책 정보 DTO
	 * @return 업데이트된 책의 응답 DTO
	 */
	UpdateBookResponse updateBookById(Long bookId, UpdateBookRequest request);

	/**
	 * 외부 API에서 지정된 개수의 도서를 가져와 저장하는 메서드입니다.
	 *
	 * @param count 가져와 저장할 도서의 개수
	 * @return 도서 목록 저장이 성공적으로 완료된 경우 성공 메시지를 포함한 ResponseEntity,
	 *         예외가 발생한 경우 적절한 HTTP 상태 코드와 오류 메시지를 포함한 ResponseEntity를 반환합니다.
	 */
	ResponseEntity<String> fetchAndSaveBooks(Long count);

	/**
	 * 주어진 책 ID에 해당하는 책을 삭제합니다.
	 *
	 * @param bookId 삭제할 책 ID
	 */
	void deleteBook(Long bookId);



	List<BookSearchResult> searchBooks(String query);
}
