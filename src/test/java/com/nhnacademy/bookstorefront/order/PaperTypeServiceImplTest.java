package com.nhnacademy.bookstorefront.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.order.dto.request.CreateWrappingTypeRequest;
import com.nhnacademy.bookstorefront.order.dto.request.UpdateWrappingTypeRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreatePaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAdminAllPaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllPaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetPaperResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.Impl.PaperTypeServiceImpl;

public class PaperTypeServiceImplTest {

	@Mock
	private OrderServiceClient orderServiceClient;

	@InjectMocks
	private PaperTypeServiceImpl paperTypeService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllPaperTypes() {
		GetAllPaperResponse response = mock(GetAllPaperResponse.class);

		when(orderServiceClient.getAllWrappingPapers())
			.thenReturn(ResponseEntity.ok(response));

		GetAllPaperResponse result = paperTypeService.getAllPaperTypes();

		assertEquals(response, result);
	}

	@Test
	public void testCreatePaper() {
		CreateWrappingTypeRequest request = mock(CreateWrappingTypeRequest.class);
		CreatePaperResponse response = mock(CreatePaperResponse.class);

		when(orderServiceClient.createPaper(any(CreateWrappingTypeRequest.class)))
			.thenReturn(ResponseEntity.ok(response));

		CreatePaperResponse result = paperTypeService.createPaper(request);

		assertEquals(response, result);
	}

	@Test
	public void testUpdatePaperTypeById() {
		Long id = 1L;
		UpdateWrappingTypeRequest request = mock(UpdateWrappingTypeRequest.class);
		GetPaperResponse response = mock(GetPaperResponse.class);

		when(orderServiceClient.updatePaper(any(UpdateWrappingTypeRequest.class), eq(id)))
			.thenReturn(ResponseEntity.ok(response));

		GetPaperResponse result = paperTypeService.updatePaperTypeById(id, request);

		assertEquals(response, result);
	}

	@Test
	public void testDeletePaperTypeById() {
		Long id = 1L;

		// deletePaperTypeById 메서드는 반환 값이 없는 void 메서드입니다.
		when(orderServiceClient.deletePaper(id)).thenReturn(ResponseEntity.ok().build());

		paperTypeService.deletePaperTypeById(id);

		// deletePaper 메서드가 한 번 호출되었는지 검증합니다.
		verify(orderServiceClient, times(1)).deletePaper(id);
	}

	@Test
	public void testGetAdminAllPaperTypes() {
		GetAdminAllPaperResponse response = mock(GetAdminAllPaperResponse.class);

		when(orderServiceClient.getAdminAllWrappingPapers())
			.thenReturn(ResponseEntity.ok(response));

		GetAdminAllPaperResponse result = paperTypeService.getAdminAllPaperTypes();

		assertEquals(response, result);
	}
}
