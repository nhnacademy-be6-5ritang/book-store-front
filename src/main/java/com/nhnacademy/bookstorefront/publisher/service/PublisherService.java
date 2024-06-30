package com.nhnacademy.bookstorefront.publisher.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.publisher.dto.response.PublisherDto;
import com.nhnacademy.bookstorefront.publisher.feignclient.PublisherServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherService {
	private final PublisherServiceClient publisherServiceClient;

	public List<PublisherDto> getPublishers() {
		return publisherServiceClient.getPublishers().getBody();
	}

	public PublisherDto getPublisher(Long publisherId) {
		return publisherServiceClient.getPublisher(publisherId).getBody();
	}

	public PublisherDto createPublisher(PublisherDto publisherDto) {
		return publisherServiceClient.createPublisher(publisherDto).getBody();
	}

	public PublisherDto updatePublisher(Long publisherId, PublisherDto publisherDto) {
		return publisherServiceClient.updatePublisher(publisherId, publisherDto).getBody();
	}

	public void deletePublisher(Long publisherId) {
		publisherServiceClient.deletePublisher(publisherId);
	}
}
