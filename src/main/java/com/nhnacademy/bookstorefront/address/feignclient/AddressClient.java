package com.nhnacademy.bookstorefront.address.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.address.dto.request.RegisterAddressRequest;
import com.nhnacademy.bookstorefront.address.dto.response.GetAddressResponse;
import com.nhnacademy.bookstorefront.address.dto.response.RegisterAddressResponse;
import com.nhnacademy.bookstorefront.global.config.FeignClientConfig;

@FeignClient(name = "address-feign-client", url = "http://localhost:8090", configuration = FeignClientConfig.class)
public interface AddressClient {

	@PostMapping("/api/addresses")
	ResponseEntity<RegisterAddressResponse> registerAddress(@RequestBody RegisterAddressRequest registerAddressRequest);

	@GetMapping("/api/addresses")
	ResponseEntity<List<GetAddressResponse>> getAddresses();
}
