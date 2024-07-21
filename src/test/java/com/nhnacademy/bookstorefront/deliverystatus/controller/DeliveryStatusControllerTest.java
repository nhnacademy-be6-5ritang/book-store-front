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
		mockMvc.perform(get("/api/deliveryStatuses/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("deliveryStatus/create-delivery-status"));
	}

	@Test
	void testUpdateDeliveryStatusForm() throws Exception {
		Long deliveryStatusId = 1L;
		GetDeliveryStatusResponse getResponse = new GetDeliveryStatusResponse(deliveryStatusId, "In Progress");
		when(deliveryStatusService.getDeliveryStatus(anyLong())).thenReturn(getResponse);

		mockMvc.perform(get("/api/deliveryStatuses/update/{deliveryStatusId}", deliveryStatusId))
			.andExpect(status().isOk())
			.andExpect(view().name("deliveryStatus/update-delivery-status"))
			.andExpect(model().attribute("deliveryStatus", getResponse));
	}

	@Test
	void testListDeliveryStatuses() throws Exception {
		GetDeliveryStatusResponse getResponse = new GetDeliveryStatusResponse(1L, "In Progress");
		List<GetDeliveryStatusResponse> statusesList = List.of(getResponse);
		when(deliveryStatusService.getDeliveryStatuses()).thenReturn(statusesList);

		mockMvc.perform(get("/api/deliveryStatuses"))
			.andExpect(status().isOk())
			.andExpect(view().name("deliveryStatus/list-delivery-status"))
			.andExpect(model().attributeExists("deliveryStatuses"))
			.andExpect(model().attribute("deliveryStatuses", statusesList));
	}

	@Test
	void testCreateDeliveryStatus() throws Exception {
		CreateDeliveryStatusRequest createRequest = new CreateDeliveryStatusRequest("New Status");

		mockMvc.perform(post("/api/deliveryStatuses")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("deliveryStatusName", createRequest.deliveryStatusName()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/deliveryStatuses"));

		verify(deliveryStatusService).createDeliveryStatus(any(CreateDeliveryStatusRequest.class));
	}

	@Test
	void testUpdateDeliveryStatus() throws Exception {
		Long deliveryStatusId = 1L;
		UpdateDeliveryStatusRequest updateRequest = new UpdateDeliveryStatusRequest("Updated");
		
		doNothing().when(deliveryStatusService).updateDeliveryStatus(anyLong(), any(UpdateDeliveryStatusRequest.class));

		mockMvc.perform(put("/api/deliveryStatuses/{deliveryStatusId}", deliveryStatusId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("deliveryStatusName", updateRequest.deliveryStatusName()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/deliveryStatuses"));

		verify(deliveryStatusService).updateDeliveryStatus(anyLong(), any(UpdateDeliveryStatusRequest.class));
	}

	@Test
	void testDeleteDeliveryStatus() throws Exception {
		Long deliveryStatusId = 1L;

		mockMvc.perform(delete("/api/deliveryStatuses/{deliveryStatusId}", deliveryStatusId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/deliveryStatuses"));

		verify(deliveryStatusService).deleteDeliveryStatus(deliveryStatusId);
	}
}