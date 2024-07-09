package com.nhnacademy.bookstorefront.point.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.nhnacademy.bookstorefront.point.dto.request.CreatePointEarningPolicyRequest;

import com.nhnacademy.bookstorefront.point.dto.request.UpdatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.response.CreatePointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.UpdatePointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.feignclient.PointServiceClient;
import com.nhnacademy.bookstorefront.point.service.PointEarningPolicyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointEarningPolicyServiceImpl implements PointEarningPolicyService {
	private final PointServiceClient pointServiceClient;
	@Override
	public CreatePointEarningPolicyResponse createPointEarningPolicy(CreatePointEarningPolicyRequest createPointEarningPolicyRequest) {
		return pointServiceClient.createPointEarningPolicy(createPointEarningPolicyRequest).getBody();
	}

	@Override
	public List<GetPointEarningPolicyResponse> getPointEarningPolicies() {
		return pointServiceClient.getPointEarningPolicies().getBody();
	}

	@Override
	public UpdatePointEarningPolicyResponse updatePointEarningPolicy(Long pointEarningPolicyId,
		UpdatePointEarningPolicyRequest updatePointEarningPolicyRequest) {
		return pointServiceClient.updatePointEarningPolicy(pointEarningPolicyId, updatePointEarningPolicyRequest).getBody();
	}

	@Override
	public void activatePointEarningPolicy(Long pointEarningPolicyId) {
		pointServiceClient.activatePointEarningPolicy(pointEarningPolicyId).getBody();
	}

	@Override
	public void deactivatePointEarningPolicy(Long pointEarningPolicyId) {
		pointServiceClient.deactivatePointEarningPolicy(pointEarningPolicyId).getBody();
	}

}
