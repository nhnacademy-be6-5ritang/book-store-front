package com.nhnacademy.bookstorefront.deliverystatus.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.deliverystatus.dto.request.CreateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.request.UpdateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.GetDeliveryStatusResponse;
import com.nhnacademy.bookstorefront.deliverystatus.service.DeliveryStatusService;

class DeliveryStatusControllerTest {

	private MockMvc mockMvc;

	@Mock
	private DeliveryStatusService deliveryStatusService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new DeliveryStatusController(deliveryStatusService)).build();
	}

	@Test
	void testCreateDeliveryStatusForm() throws Exception {
		mockMvc.perform(get("/delivery-statuses/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("delivery-status/create-delivery-status"));
	}

	@Test
	void testUpdateDeliveryStatusForm() throws Exception {
		Long deliveryStatusId = 1L;
		GetDeliveryStatusResponse getResponse = new GetDeliveryStatusResponse(deliveryStatusId, "In Progress");
		when(deliveryStatusService.getDeliveryStatus(anyLong())).thenReturn(getResponse);

		mockMvc.perform(get("/delivery-statuses/update/{deliveryStatusId}", deliveryStatusId))
			.andExpect(status().isOk())
			.andExpect(view().name("delivery-status/update-delivery-status"))
			.andExpect(model().attribute("deliveryStatus", getResponse));
	}

	@Test
	void testListDeliveryStatuses() throws Exception {
		GetDeliveryStatusResponse getResponse = new GetDeliveryStatusResponse(1L, "In Progress");
		List<GetDeliveryStatusResponse> statusesList = List.of(getResponse);
		when(deliveryStatusService.getDeliveryStatuses()).thenReturn(statusesList);

		mockMvc.perform(get("/delivery-statuses"))
			.andExpect(status().isOk())
			.andExpect(view().name("delivery-status/list-delivery-status"))
			.andExpect(model().attributeExists("deliveryStatuses"))
			.andExpect(model().attribute("deliveryStatuses", statusesList));
	}

	@Test
	void testCreateDeliveryStatus() throws Exception {
		CreateDeliveryStatusRequest createRequest = new CreateDeliveryStatusRequest("New Status");

		mockMvc.perform(post("/delivery-statuses")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("deliveryStatusName", createRequest.deliveryStatusName()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/delivery-statuses"));

		verify(deliveryStatusService).createDeliveryStatus(any(CreateDeliveryStatusRequest.class));
	}

	@Test
	void testUpdateDeliveryStatus() throws Exception {
		Long deliveryStatusId = 1L;
		UpdateDeliveryStatusRequest updateRequest = new UpdateDeliveryStatusRequest("Updated");

		doNothing().when(deliveryStatusService).updateDeliveryStatus(anyLong(), any(UpdateDeliveryStatusRequest.class));

		mockMvc.perform(put("/delivery-statuses/{deliveryStatusId}", deliveryStatusId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("deliveryStatusName", updateRequest.deliveryStatusName()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/delivery-statuses"));

		verify(deliveryStatusService).updateDeliveryStatus(anyLong(), any(UpdateDeliveryStatusRequest.class));
	}

	@Test
	void testDeleteDeliveryStatus() throws Exception {
		Long deliveryStatusId = 1L;

		mockMvc.perform(delete("/delivery-statuses/{deliveryStatusId}", deliveryStatusId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/delivery-statuses"));

		verify(deliveryStatusService).deleteDeliveryStatus(deliveryStatusId);
	}
}