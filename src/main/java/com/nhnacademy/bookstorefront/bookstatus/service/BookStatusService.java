package com.nhnacademy.bookstorefront.bookstatus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.bookstatus.dto.response.BookStatusDto;
import com.nhnacademy.bookstorefront.bookstatus.feignclient.BookStatusServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookStatusService {
	private final BookStatusServiceClient bookStatusServiceClient;

	public List<BookStatusDto> getBookStatuses() {
		return bookStatusServiceClient.getBookStatuses().getBody();
	}

	public BookStatusDto getBookStatus(Long bookStatusId) {
		return bookStatusServiceClient.getBookStatus(bookStatusId).getBody();
	}

	public BookStatusDto createBookStatus(BookStatusDto bookStatusDto) {
		return bookStatusServiceClient.createBookStatus(bookStatusDto).getBody();
	}

	public BookStatusDto updateBookStatus(Long bookStatusId, BookStatusDto bookStatusDto) {
		return bookStatusServiceClient.updateBookStatus(bookStatusId, bookStatusDto).getBody();
	}

	public void deleteBookStatus(Long bookStatusId) {
		bookStatusServiceClient.deleteBookStatus(bookStatusId);
	}
}
