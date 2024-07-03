package com.nhnacademy.bookstorefront.publisher.service;

import java.util.List;

import com.nhnacademy.bookstorefront.publisher.dto.response.PublisherDto;

/**
 * 외부 Publisher 서비스와 통신하여 카테고리 정보를 관리하는 인터페이스입니다.
 *
 * @version 1.0
 */
public interface PublisherService {

	/**
	 * 모든 출판사 목록을 조회합니다.
	 *
	 * @return 출판사 목록
	 */
	List<PublisherDto> getPublishers();

	/**
	 * 지정된 출판사 ID에 해당하는 출판사 정보를 조회합니다.
	 *
	 * @param publisherId 출판사 ID
	 * @return 출판사 정보
	 */
	PublisherDto getPublisher(Long publisherId);

	/**
	 * 새로운 출판사를 생성합니다.
	 *
	 * @param publisherDto 생성할 출판사 정보
	 * @return 생성된 출판사 정보
	 */
	PublisherDto createPublisher(PublisherDto publisherDto);

	/**
	 * 지정된 출판사 ID에 해당하는 출판사 정보를 수정합니다.
	 *
	 * @param publisherId   수정할 출판사 ID
	 * @param publisherDto 수정할 출판사 정보
	 * @return 수정된 출판사 정보
	 */
	PublisherDto updatePublisher(Long publisherId, PublisherDto publisherDto);

	/**
	 * 지정된 출판사 ID에 해당하는 출판사를 삭제합니다.
	 *
	 * @param publisherId 삭제할 출판사 ID
	 */
	void deletePublisher(Long publisherId);
}