package com.nhnacademy.bookstorefront.upload.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "upload-feign-client", url = "http://localhost:8090/api/uploads")
public interface UploadServiceClient {

	@PostMapping(consumes = "multipart/form-data")
	ResponseEntity<String> upload(@RequestPart("file") MultipartFile file);

}
