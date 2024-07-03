package com.nhnacademy.bookstorefront.publisher.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.publisher.dto.response.PublisherDto;
import com.nhnacademy.bookstorefront.publisher.feignclient.PublisherServiceClient;
import com.nhnacademy.bookstorefront.publisher.service.PublisherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
	private final PublisherServiceClient publisherServiceClient;

	@Override
	public List<PublisherDto> getPublishers() {
		return publisherServiceClient.getPublishers().getBody();
	}

	@Override
	public PublisherDto getPublisher(Long publisherId) {
		return publisherServiceClient.getPublisher(publisherId).getBody();
	}

	@Override
	public PublisherDto createPublisher(PublisherDto publisherDto) {
		return publisherServiceClient.createPublisher(publisherDto).getBody();
	}

	@Override
	public PublisherDto updatePublisher(Long publisherId, PublisherDto publisherDto) {
		return publisherServiceClient.updatePublisher(publisherId, publisherDto).getBody();
	}

	@Override
	public void deletePublisher(Long publisherId) {
		publisherServiceClient.deletePublisher(publisherId);
	}
}
