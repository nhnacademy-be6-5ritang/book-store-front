package com.nhnacademy.bookstorefront.address.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.address.dto.request.RegisterAddressRequest;
import com.nhnacademy.bookstorefront.address.dto.request.UpdateAddressRequest;
import com.nhnacademy.bookstorefront.address.dto.response.GetAddressResponse;
import com.nhnacademy.bookstorefront.address.dto.response.RegisterAddressResponse;
import com.nhnacademy.bookstorefront.address.dto.response.UpdateAddressResponse;
import com.nhnacademy.bookstorefront.address.feignclient.AddressClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {
	private final AddressClient addressClient;

	public ResponseEntity<RegisterAddressResponse> registerAddress(RegisterAddressRequest registerAddressRequest) {
		return addressClient.registerAddress(registerAddressRequest);
	}

	public ResponseEntity<List<GetAddressResponse>> getAddresses() {
		return addressClient.getAddresses();
	}

	public ResponseEntity<UpdateAddressResponse> updateAddress(
		Long addressId, UpdateAddressRequest updateAddressRequest
	) {
		return addressClient.updateAddress(addressId, updateAddressRequest);
	}

	public ResponseEntity<Void> deleteAddress(Long addressId) {
		return addressClient.deleteAddress(addressId);
	}
	
	public ResponseEntity<Void> setDefaultAddress(Long addressId) {
		return addressClient.setDefaultAddress(addressId);
	}

	public Optional<GetAddressResponse> getDefaultAddress() {
		return addressClient.getDefaultAddress().getBody();
	}
}
