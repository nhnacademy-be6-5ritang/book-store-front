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

/**
 * @author 김태환
 * 주소 관련 비즈니스 로직을 처리하는 서비스입니다.
 */
@Service
@RequiredArgsConstructor
public class AddressService {
	private final AddressClient addressClient;

	/**
	 * 새로운 주소를 등록합니다.
	 *
	 * @param registerAddressRequest 주소 등록 요청 정보를 담고 있는 {@link RegisterAddressRequest} 객체
	 * @return 주소 등록 결과를 담고 있는 {@link ResponseEntity} 객체
	 */
	public ResponseEntity<RegisterAddressResponse> registerAddress(RegisterAddressRequest registerAddressRequest) {
		return addressClient.registerAddress(registerAddressRequest);
	}

	/**
	 * 사용자의 모든 주소를 조회합니다.
	 *
	 * @return 사용자의 모든 주소를 담고 있는 {@link ResponseEntity} 객체
	 */
	public ResponseEntity<List<GetAddressResponse>> getAddresses() {
		return addressClient.getAddresses();
	}

	/**
	 * 주어진 주소 ID에 해당하는 주소를 수정합니다.
	 *
	 * @param addressId 수정할 주소의 ID
	 * @param updateAddressRequest 주소 수정 요청 정보를 담고 있는 {@link UpdateAddressRequest} 객체
	 * @return 주소 수정 결과를 담고 있는 {@link ResponseEntity} 객체
	 */
	public ResponseEntity<UpdateAddressResponse> updateAddress(
		Long addressId, UpdateAddressRequest updateAddressRequest
	) {
		return addressClient.updateAddress(addressId, updateAddressRequest);
	}

	/**
	 * 주어진 주소 ID에 해당하는 주소를 삭제합니다.
	 *
	 * @param addressId 삭제할 주소의 ID
	 * @return 삭제 결과를 담고 있는 {@link ResponseEntity} 객체
	 */
	public ResponseEntity<Void> deleteAddress(Long addressId) {
		return addressClient.deleteAddress(addressId);
	}

	/**
	 * 주어진 주소 ID를 기본 주소로 설정합니다.
	 *
	 * @param addressId 기본 주소로 설정할 주소의 ID
	 * @return 기본 주소 설정 결과를 담고 있는 {@link ResponseEntity} 객체
	 */
	public ResponseEntity<Void> setDefaultAddress(Long addressId) {
		return addressClient.setDefaultAddress(addressId);
	}

	/**
	 * 기본 주소를 조회합니다.
	 *
	 * @return 기본 주소를 담고 있는 {@link Optional} 객체
	 */
	public Optional<GetAddressResponse> getDefaultAddress() {
		return addressClient.getDefaultAddress().getBody();
	}
}
