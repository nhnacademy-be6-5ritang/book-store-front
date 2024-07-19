package com.nhnacademy.bookstorefront.order.service.Impl;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.bookstorefront.global.controller.payload.ErrorStatus;
import com.nhnacademy.bookstorefront.order.dto.request.CreateWrappingTypeRequest;
import com.nhnacademy.bookstorefront.order.dto.request.UpdateWrappingTypeRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreatePaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAdminAllPaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllPaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetPaperResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.PaperTypeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaperTypeServiceImpl implements PaperTypeService {
	private final OrderServiceClient orderServiceClient;

	@Override
	public GetAllPaperResponse getAllPaperTypes() {
		return orderServiceClient.getAllWrappingPapers().getBody();
	}

	@Override
	public CreatePaperResponse createPaper(CreateWrappingTypeRequest createWrappingTypeRequest) {
		return orderServiceClient.createPaper(createWrappingTypeRequest).getBody();
	}

	@Override
	public GetPaperResponse updatePaperTypeById(Long id, UpdateWrappingTypeRequest updateWrappingTypeRequest) {
		return orderServiceClient.updatePaper(updateWrappingTypeRequest, id).getBody();
	}

	@Override
	public void deletePaperTypeById(Long id) {
		orderServiceClient.deletePaper(id).getBody();
	}

	@Override
	public GetAdminAllPaperResponse getAdminAllPaperTypes() {
		return orderServiceClient.getAdminAllWrappingPapers().getBody();
	}
}
