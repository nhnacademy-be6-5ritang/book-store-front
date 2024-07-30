package com.nhnacademy.bookstorefront.address.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.address.dto.request.RegisterAddressRequest;
import com.nhnacademy.bookstorefront.address.dto.request.UpdateAddressRequest;
import com.nhnacademy.bookstorefront.address.service.AddressService;
import com.nhnacademy.bookstorefront.global.controller.GlobalDataControllerAdvice;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private AddressService addressService;

	@MockBean
	private GlobalDataControllerAdvice globalDataControllerAdvice;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new AddressController(addressService))
			.setControllerAdvice(globalDataControllerAdvice)
			.build();
	}

	@Test
	void testAddressPage() throws Exception {
		when(addressService.getAddresses()).thenReturn(ResponseEntity.ok().body(Collections.emptyList()));

		mockMvc.perform(get("/addresses/my-address"))
			.andExpect(status().isOk())
			.andExpect(view().name("address/address"))
			.andExpect(model().attributeExists("myAddresses"));
	}

	@Test
	void testAddAddress() throws Exception {
		RegisterAddressRequest request = RegisterAddressRequest.builder()
			.alias("Home")
			.postCode("12345")
			.baseAddress("123 Main St")
			.detailAddress("Apt 101")
			.build();

		mockMvc.perform(post("/addresses")
				.flashAttr("registerAddressRequest", request))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/addresses/my-address"))
			.andExpect(flash().attributeExists("responseMessage"));
	}

	@Test
	void testDeleteAddress() throws Exception {
		when(addressService.deleteAddress(1L)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

		mockMvc.perform(delete("/addresses/1"))
			.andExpect(status().isOk());
	}

	@Test
	void testUpdateAddress() throws Exception {
		UpdateAddressRequest request = UpdateAddressRequest.builder()
			.alias("Office")
			.postCode("54321")
			.baseAddress("456 Elm St")
			.detailAddress("Suite 202")
			.build();

		mockMvc.perform(post("/addresses/1")
				.flashAttr("updateAddressRequest", request))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/addresses/my-address"))
			.andExpect(flash().attributeExists("responseMessage"));
	}

	@Test
	void testSetDefaultAddress() throws Exception {
		when(addressService.setDefaultAddress(1L)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

		mockMvc.perform(put("/addresses/1/default"))
			.andExpect(status().isOk());
	}
}