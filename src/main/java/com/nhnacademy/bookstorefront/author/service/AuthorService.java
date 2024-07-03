package com.nhnacademy.bookstorefront.author.service;

import java.util.List;

import com.nhnacademy.bookstorefront.author.dto.response.AuthorDto;

/**
 * 외부 Author 서비스와 통신하여 작가 정보를 관리하는 인터페이스입니다.
 *
 * @version 1.0
 */
public interface AuthorService {

	/**
	 * 모든 작가 정보를 조회합니다.
	 *
	 * @return 작가 목록
	 */
	List<AuthorDto> getAuthors();

	/**
	 * 주어진 작가 ID에 해당하는 작가 정보를 조회합니다.
	 *
	 * @param authorId 작가 ID
	 * @return 작가 정보
	 */
	AuthorDto getAuthor(Long authorId);

	/**
	 * 새로운 작가 정보를 생성합니다.
	 *
	 * @param authorDto 생성할 작가 정보 DTO
	 * @return 생성된 작가 정보
	 */
	AuthorDto createAuthor(AuthorDto authorDto);

	/**
	 * 주어진 작가 ID에 해당하는 작가 정보를 수정합니다.
	 *
	 * @param authorId  수정할 작가 ID
	 * @param authorDto 수정할 작가 정보 DTO
	 * @return 수정된 작가 정보
	 */
	AuthorDto updateAuthor(Long authorId, AuthorDto authorDto);

	/**
	 * 주어진 작가 ID에 해당하는 작가 정보를 삭제합니다.
	 *
	 * @param authorId 삭제할 작가 ID
	 */
	void deleteAuthor(Long authorId);
}
