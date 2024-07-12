package com.nhnacademy.bookstorefront.point.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.point.dto.request.CreatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.request.UpdatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.response.CreatePointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetAllPointTransactionResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointTransactionResponse;
import com.nhnacademy.bookstorefront.point.dto.response.UpdatePointEarningPolicyResponse;

@FeignClient(name = "point-feign-service", url = "http://localhost:8090/api")
public interface PointServiceClient {
	@PostMapping("/point-earning-policies")
	ResponseEntity<CreatePointEarningPolicyResponse> createPointEarningPolicy (@RequestBody CreatePointEarningPolicyRequest createPointEarningPolicyRequest);

	@GetMapping("/point-earning-policies")
	ResponseEntity<List<GetPointEarningPolicyResponse>> getPointEarningPolicies();

	@PatchMapping("/point-earning-policies/{pointEarningPolicyId}")
	ResponseEntity<UpdatePointEarningPolicyResponse> updatePointEarningPolicy(@PathVariable Long pointEarningPolicyId, @RequestBody UpdatePointEarningPolicyRequest updatePointEarningPolicyRequest);

	@PatchMapping("/point-earning-policies/{pointEarningPolicyId}/activate")
	ResponseEntity<Void> activatePointEarningPolicy(@PathVariable Long pointEarningPolicyId);

	@PatchMapping("/point-earning-policies/{pointEarningPolicyId}/deactivate")
	ResponseEntity<Void> deactivatePointEarningPolicy(@PathVariable Long pointEarningPolicyId);

	@GetMapping("/point-transactions")
	ResponseEntity<Page<GetPointTransactionResponse>> getPointTransactions(Pageable pageable);

	@GetMapping("/point-transactions/all")
	ResponseEntity<Page<GetAllPointTransactionResponse>> getAllPointTransactions(Pageable pageable);

}
