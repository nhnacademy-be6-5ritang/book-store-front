package com.nhnacademy.bookstorefront.address.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.address.dto.request.RegisterAddressRequest;
import com.nhnacademy.bookstorefront.address.dto.request.UpdateAddressRequest;
import com.nhnacademy.bookstorefront.address.dto.response.GetAddressResponse;
import com.nhnacademy.bookstorefront.address.dto.response.RegisterAddressResponse;
import com.nhnacademy.bookstorefront.address.dto.response.UpdateAddressResponse;
import com.nhnacademy.bookstorefront.address.feignclient.AddressClient;

class AddressServiceTest {

	@Mock
	private AddressClient addressClient;

	@InjectMocks
	private AddressService addressService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testRegisterAddress() {
		RegisterAddressRequest request = RegisterAddressRequest.builder()
			.alias("Home")
			.postCode("12345")
			.baseAddress("123 Main St")
			.detailAddress("Apt 101")
			.build();

		RegisterAddressResponse response = new RegisterAddressResponse(
			1L, 1L, "12345", "123 Main St", "Apt 101", "Home"
		);

		when(addressClient.registerAddress(any(RegisterAddressRequest.class)))
			.thenReturn(ResponseEntity.ok(response));

		ResponseEntity<RegisterAddressResponse> result = addressService.registerAddress(request);

		assertThat(result).isNotNull();
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().id()).isEqualTo(1L);
		assertThat(result.getBody().alias()).isEqualTo("Home");
	}

	@Test
	void testGetAddresses() {
		GetAddressResponse addressResponse = new GetAddressResponse(
			1L, "12345", "123 Main St", "Apt 101", "Home", false
		);
		List<GetAddressResponse> responseList = Collections.singletonList(addressResponse);

		when(addressClient.getAddresses()).thenReturn(ResponseEntity.ok(responseList));

		ResponseEntity<List<GetAddressResponse>> result = addressService.getAddresses();

		assertThat(result).isNotNull();
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody()).hasSize(1);
		assertThat(result.getBody().get(0).id()).isEqualTo(1L);
		assertThat(result.getBody().get(0).alias()).isEqualTo("Home");
		assertThat(result.getBody().get(0).isDefault()).isFalse();
	}

	@Test
	void testUpdateAddress() {
		UpdateAddressRequest request = UpdateAddressRequest.builder()
			.alias("Office")
			.postCode("54321")
			.baseAddress("456 Elm St")
			.detailAddress("Suite 202")
			.build();

		UpdateAddressResponse response = new UpdateAddressResponse(
			1L, 1L, "54321", "456 Elm St", "Suite 202", "Office"
		);

		when(addressClient.updateAddress(anyLong(), any(UpdateAddressRequest.class)))
			.thenReturn(ResponseEntity.ok(response));

		ResponseEntity<UpdateAddressResponse> result = addressService.updateAddress(1L, request);

		assertThat(result).isNotNull();
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().id()).isEqualTo(1L);
		assertThat(result.getBody().alias()).isEqualTo("Office");
	}

	@Test
	void testDeleteAddress() {
		// No return value to verify, just ensure no exception is thrown
		when(addressClient.deleteAddress(anyLong())).thenReturn(ResponseEntity.ok().build());

		ResponseEntity<Void> result = addressService.deleteAddress(1L);

		assertThat(result).isNotNull();
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	void testSetDefaultAddress() {
		// No return value to verify, just ensure no exception is thrown
		when(addressClient.setDefaultAddress(anyLong())).thenReturn(ResponseEntity.ok().build());

		ResponseEntity<Void> result = addressService.setDefaultAddress(1L);

		assertThat(result).isNotNull();
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	void testGetDefaultAddress() {
		GetAddressResponse addressResponse = new GetAddressResponse(
			1L, "12345", "123 Main St", "Apt 101", "Home", true
		);

		when(addressClient.getDefaultAddress()).thenReturn(ResponseEntity.ok(Optional.of(addressResponse)));

		Optional<GetAddressResponse> result = addressService.getDefaultAddress();

		assertThat(result).isNotNull();
		assertThat(result).isPresent();
		assertThat(result.get().id()).isEqualTo(1L);
		assertThat(result.get().alias()).isEqualTo("Home");
		assertThat(result.get().isDefault()).isTrue();
	}
}