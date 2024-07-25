package com.nhnacademy.bookstorefront.delivery;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.address.service.AddressService;
import com.nhnacademy.bookstorefront.cache.service.impl.CacheServiceImpl;
import com.nhnacademy.bookstorefront.delivery.controller.DeliveryController;
import com.nhnacademy.bookstorefront.delivery.dto.request.CreateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryByOrderIdRequest;
import com.nhnacademy.bookstorefront.delivery.dto.response.CreateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.GetDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.service.DeliveryService;

@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest {

	@MockBean
	private DeliveryService deliveryService;

	@MockBean
	private AddressService addressService;

	@MockBean
	private CacheServiceImpl cacheService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		// 설정한 MockMvc 인스턴스를 사용합니다.
		mockMvc = MockMvcBuilders.standaloneSetup(new DeliveryController(deliveryService, addressService))
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	void testGetDeliveriesDeliveryPage() throws Exception {
		List<GetDeliveryResponse> deliveriesList = List.of(
			new GetDeliveryResponse(
				1L, "Sender1", "1234567890", LocalDateTime.now(), "Sender Address 1",
				"Receiver1", "0987654321", LocalDateTime.now(), "Receiver Address 1",
				101L, "Delivered"
			),
			new GetDeliveryResponse(
				2L, "Sender2", "1234567891", LocalDateTime.now(), "Sender Address 2",
				"Receiver2", "0987654322", LocalDateTime.now(), "Receiver Address 2",
				102L, "Pending"
			)
		);
		Page<GetDeliveryResponse> mockPage = new PageImpl<>(deliveriesList);
		when(deliveryService.getDeliveriesByUserId(any(Pageable.class))).thenReturn(mockPage);

		mockMvc.perform(get("/api/deliveries/me/page")
				.param("page", "1")
				.param("size", "10"))
			.andExpect(status().isOk())
			.andExpect(view().name("delivery/list-delivery"))
			.andExpect(model().attribute("deliveries", mockPage));
	}

	@Test
	void testGetDelivery() throws Exception {
		GetDeliveryResponse mockDeliveryResponse = new GetDeliveryResponse(
			1L, "Sender1", "1234567890", LocalDateTime.now(), "Sender Address 1",
			"Receiver1", "0987654321", LocalDateTime.now(), "Receiver Address 1",
			101L, "Delivered"
		);
		when(deliveryService.getDelivery(anyLong())).thenReturn(mockDeliveryResponse);
		
		mockMvc.perform(get("/api/deliveries/delivery/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("delivery/get-delivery"))
			.andExpect(model().attribute("delivery", mockDeliveryResponse));
	}

	@Test
	void testGetDeliveriesPage() throws Exception {
		mockMvc.perform(get("/api/deliveries/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("delivery/delivery"))
			.andExpect(model().attribute("orderListId", 1L));
	}

	@Test
	void testCreateDeliveriesPage() throws Exception {
		CreateDeliveryRequest request = new CreateDeliveryRequest(
			"Receiver", "1234567890", LocalDateTime.now(), "Address 1", "Address 2"
		);
		CreateDeliveryResponse response = new CreateDeliveryResponse(
			1L, "Sender", "0987654321", LocalDateTime.now(), "Sender Address",
			"Receiver", "1234567890", LocalDateTime.now(), "Receiver Address",
			1L, 1L
		);

		when(deliveryService.createDelivery(any(CreateDeliveryRequest.class))).thenReturn(response);

		mockMvc.perform(post("/api/deliveries/1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("deliveryReceiver", "Receiver")
				.param("deliveryReceiverPhone", "1234567890")
				.param("deliveryReceiverDate", LocalDateTime.now().toString())
				.param("deliveryReceiverAddress", "Address 1")
				.param("deliveryReceiverAddress2", "Address 2")
			)
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/coupons/orders/1/users/1"));
	}

	@Test
	void testGetDeliveriesCartOrder() throws Exception {
		mockMvc.perform(get("/api/deliveries/cart-order/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("delivery/delivery-cart"))
			.andExpect(model().attribute("orderInfoId", "1"));
	}

	@Test
	void testCreateDeliveriesCartOrder() throws Exception {
		CreateDeliveryRequest request = new CreateDeliveryRequest(
			"Receiver", "1234567890", LocalDateTime.now(), "Address 1", "Address 2"
		);
		CreateDeliveryResponse response = new CreateDeliveryResponse(
			1L, "Sender", "0987654321", LocalDateTime.now(), "Sender Address",
			"Receiver", "1234567890", LocalDateTime.now(), "Receiver Address",
			1L, 1L
		);

		when(deliveryService.createDelivery(any(CreateDeliveryRequest.class))).thenReturn(response);

		mockMvc.perform(post("/api/deliveries/cart-order/1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("deliveryReceiver", "Receiver")
				.param("deliveryReceiverPhone", "1234567890")
				.param("deliveryReceiverDate", LocalDateTime.now().toString())
				.param("deliveryReceiverAddress", "Address 1")
				.param("deliveryReceiverAddress2", "Address 2")
			)
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/coupons/orders/users/1/1"));
	}

	@Test
	void testSenderPage() throws Exception {
		mockMvc.perform(get("/api/deliveries/1/sender"))
			.andExpect(status().isOk())
			.andExpect(view().name("delivery/sender"))
			.andExpect(model().attribute("orderId", 1L));
	}

	@Test
	void testUpdateDeliveryByOrderId() throws Exception {
		UpdateDeliveryByOrderIdRequest request = new UpdateDeliveryByOrderIdRequest(
			"Sender", "Sender Address", "Sender Address 2", "Sender Phone"
		);

		mockMvc.perform(post("/api/deliveries/1/sender")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("senderName", "Sender")
				.param("senderAddress", "Sender Address")
				.param("senderAddress2", "Sender Address 2")
				.param("senderPhone", "Sender Phone")
			)
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/order-status/going"));
	}
}
