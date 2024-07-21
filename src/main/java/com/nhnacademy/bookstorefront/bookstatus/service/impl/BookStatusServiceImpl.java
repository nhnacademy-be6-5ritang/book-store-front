package com.nhnacademy.bookstorefront.bookstatus.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.bookstatus.dto.response.BookStatusDto;
import com.nhnacademy.bookstorefront.bookstatus.feignclient.BookStatusServiceClient;
import com.nhnacademy.bookstorefront.bookstatus.service.BookStatusService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookStatusServiceImpl implements BookStatusService {
	private final BookStatusServiceClient bookStatusServiceClient;

	@Override
	public List<BookStatusDto> getBookStatuses() {
		return bookStatusServiceClient.getBookStatuses().getBody();
	}

	@Override
	public BookStatusDto getBookStatus(Long bookStatusId) {
		return bookStatusServiceClient.getBookStatus(bookStatusId).getBody();
	}

	@Override
	public void createBookStatus(BookStatusDto bookStatusDto) {
		bookStatusServiceClient.createBookStatus(bookStatusDto);
	}

	@Override
	public void updateBookStatus(Long bookStatusId, BookStatusDto bookStatusDto) {
		bookStatusServiceClient.updateBookStatus(bookStatusId, bookStatusDto);
	}

	@Override
	public void deleteBookStatus(Long bookStatusId) {
		bookStatusServiceClient.deleteBookStatus(bookStatusId);
	}
}
