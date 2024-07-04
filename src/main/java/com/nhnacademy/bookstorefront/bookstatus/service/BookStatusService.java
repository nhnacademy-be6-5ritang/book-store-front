package com.nhnacademy.bookstorefront.bookstatus.service;

import java.util.List;

import com.nhnacademy.bookstorefront.bookstatus.dto.response.BookStatusDto;

/**
 * 외부 Book Status 서비스와 통신하여 책 상태 정보를 관리하는 인터페이스입니다.
 *
 * @version 1.0
 */
public interface BookStatusService {

	/**
	 * 모든 책 상태 정보를 조회합니다.
	 *
	 * @return 모든 책 상태 정보 목록
	 */
	List<BookStatusDto> getBookStatuses();

	/**
	 * 주어진 책 상태 ID에 해당하는 책 상태 정보를 조회합니다.
	 *
	 * @param bookStatusId 책 상태 ID
	 * @return 책 상태 정보
	 */
	BookStatusDto getBookStatus(Long bookStatusId);

	/**
	 * 새로운 책 상태를 생성합니다.
	 *
	 * @param bookStatusDto 생성할 책 상태 정보 DTO
	 * @return 생성된 책 상태 정보 DTO
	 */
	BookStatusDto createBookStatus(BookStatusDto bookStatusDto);

	/**
	 * 주어진 책 상태 ID에 해당하는 책 상태를 업데이트합니다.
	 *
	 * @param bookStatusId   업데이트할 책 상태 ID
	 * @param bookStatusDto 업데이트할 책 상태 정보 DTO
	 * @return 업데이트된 책 상태 정보 DTO
	 */
	BookStatusDto updateBookStatus(Long bookStatusId, BookStatusDto bookStatusDto);

	/**
	 * 주어진 책 상태 ID에 해당하는 책 상태를 삭제합니다.
	 *
	 * @param bookStatusId 삭제할 책 상태 ID
	 */
	void deleteBookStatus(Long bookStatusId);
}