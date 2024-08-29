package com.nhnacademy.bookstorefront.publisher.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.publisher.dto.response.PublisherDto;
import com.nhnacademy.bookstorefront.publisher.feignclient.PublisherServiceClient;
import com.nhnacademy.bookstorefront.publisher.service.PublisherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
	private final PublisherServiceClient publisherServiceClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<PublisherDto> getPublishers(Pageable pageable) {
		return publisherServiceClient.getPublishers(pageable).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public PublisherDto getPublisher(Long publisherId) {
		return publisherServiceClient.getPublisher(publisherId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void createPublisher(PublisherDto publisherDto) {
		publisherServiceClient.createPublisher(publisherDto);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void updatePublisher(Long publisherId, PublisherDto publisherDto) {
		publisherServiceClient.updatePublisher(publisherId, publisherDto);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void deletePublisher(Long publisherId) {
		publisherServiceClient.deletePublisher(publisherId);
	}
}
