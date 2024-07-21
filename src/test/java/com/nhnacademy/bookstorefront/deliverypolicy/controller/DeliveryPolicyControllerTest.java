package com.nhnacademy.bookstorefront.deliverypolicy.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.CreateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.request.UpdateDeliveryPolicyRequest;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPoliciesResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.service.DeliveryPolicyService;

class DeliveryPolicyControllerTest {

	private MockMvc mockMvc;

	@Mock
	private DeliveryPolicyService deliveryPolicyService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new DeliveryPolicyController(deliveryPolicyService)).build();
	}

	@Test
	void testCreateDeliveryPolicyForm() throws Exception {
		mockMvc.perform(get("/api/deliveryPolicies/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("/deliveryPolicy/create-delivery-policy"));
	}

	@Test
	void testUpdateDeliveryPolicyForm() throws Exception {
		Long deliveryPolicyId = 1L;
		GetDeliveryPolicyResponse getResponse = new GetDeliveryPolicyResponse(
			deliveryPolicyId,
			"Free Shipping",
			BigDecimal.valueOf(0),
			"Free shipping on orders over $50",
			BigDecimal.valueOf(50)
		);
		when(deliveryPolicyService.getDeliveryPolicy(anyLong())).thenReturn(getResponse);

		mockMvc.perform(get("/api/deliveryPolicies/update/{deliveryPolicyId}", deliveryPolicyId))
			.andExpect(status().isOk())
			.andExpect(view().name("/deliveryPolicy/update-delivery-policy"))
			.andExpect(model().attribute("deliveryPolicy", getResponse));
	}

	@Test
	void testGetDeliveryPolicy() throws Exception {
		Long deliveryPolicyId = 1L;
		GetDeliveryPolicyResponse getResponse = new GetDeliveryPolicyResponse(
			deliveryPolicyId,
			"Free Shipping",
			BigDecimal.valueOf(0),
			"Free shipping on orders over $50",
			BigDecimal.valueOf(50)
		);
		when(deliveryPolicyService.getDeliveryPolicy(anyLong())).thenReturn(getResponse);

		mockMvc.perform(get("/api/deliveryPolicies/{deliveryPolicyId}", deliveryPolicyId))
			.andExpect(status().isOk())
			.andExpect(view().name("deliveryPolicy/get-delivery-policy"))
			.andExpect(model().attribute("deliveryPolicy", getResponse));
	}

	@Test
	void testListDeliveryPolicies() throws Exception {
		GetDeliveryPoliciesResponse response = new GetDeliveryPoliciesResponse(
			1L,
			"Free Shipping"
		);
		List<GetDeliveryPoliciesResponse> policiesList = List.of(response);

		when(deliveryPolicyService.getDeliveryPolicies()).thenReturn(policiesList);
		
		mockMvc.perform(get("/api/deliveryPolicies"))
			.andExpect(status().isOk())
			.andExpect(view().name("deliveryPolicy/list-delivery-policy"))
			.andExpect(model().attributeExists("deliveryPolicies"))
			.andExpect(model().attribute("deliveryPolicies", policiesList));
	}

	@Test
	void testCreateDeliveryPolicy() throws Exception {
		CreateDeliveryPolicyRequest createRequest = new CreateDeliveryPolicyRequest(
			"Free Shipping",
			"Free shipping on orders over $50",
			BigDecimal.valueOf(0),
			BigDecimal.valueOf(50)
		);

		mockMvc.perform(post("/api/deliveryPolicies")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("deliveryPolicyName", createRequest.deliveryPolicyName())
				.param("deliveryPolicyContent", createRequest.deliveryPolicyContent())
				.param("deliveryPolicyPrice", createRequest.deliveryPolicyPrice().toString())
				.param("deliveryPolicyStandardPrice", createRequest.deliveryPolicyStandardPrice().toString()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/deliveryPolicies"));

		verify(deliveryPolicyService).createDeliveryPolicy(any(CreateDeliveryPolicyRequest.class));
	}

	@Test
	void testUpdateDeliveryPolicy() throws Exception {
		Long deliveryPolicyId = 1L;
		UpdateDeliveryPolicyRequest updateRequest = new UpdateDeliveryPolicyRequest(
			"Updated Policy",
			"Updated policy content",
			BigDecimal.valueOf(10),
			BigDecimal.valueOf(100)
		);

		doNothing().when(deliveryPolicyService).updateDeliveryPolicy(anyLong(), any(UpdateDeliveryPolicyRequest.class));

		mockMvc.perform(put("/api/deliveryPolicies/{deliveryPolicyId}", deliveryPolicyId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("deliveryPolicyName", updateRequest.deliveryPolicyName())
				.param("deliveryPolicyContent", updateRequest.deliveryPolicyContent())
				.param("deliveryPolicyPrice", updateRequest.deliveryPolicyPrice().toString())
				.param("deliveryPolicyStandardPrice", updateRequest.deliveryPolicyStandardPrice().toString()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/deliveryPolicies"));

		verify(deliveryPolicyService).updateDeliveryPolicy(anyLong(), any(UpdateDeliveryPolicyRequest.class));
	}

	@Test
	void testDeleteDeliveryPolicy() throws Exception {
		Long deliveryPolicyId = 1L;

		mockMvc.perform(delete("/api/deliveryPolicies/{deliveryPolicyId}", deliveryPolicyId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/deliveryPolicies"));

		verify(deliveryPolicyService).deleteDeliveryPolicy(deliveryPolicyId);
	}
}