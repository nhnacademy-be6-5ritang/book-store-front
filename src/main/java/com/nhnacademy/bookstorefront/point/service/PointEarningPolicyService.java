package com.nhnacademy.bookstorefront.point.service;

import java.util.List;

import com.nhnacademy.bookstorefront.point.dto.request.CreatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.request.UpdatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.response.CreatePointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.UpdatePointEarningPolicyResponse;

public interface PointEarningPolicyService {
	CreatePointEarningPolicyResponse createPointEarningPolicy(
		CreatePointEarningPolicyRequest createPointEarningPolicyRequest
	);
	List<GetPointEarningPolicyResponse> getPointEarningPolicies();

	UpdatePointEarningPolicyResponse updatePointEarningPolicy(
		Long pointEarningPolicyId, UpdatePointEarningPolicyRequest updatePointEarningPolicyRequest
	);

	void activatePointEarningPolicy(Long pointEarningPolicyId);

	void deactivatePointEarningPolicy(Long pointEarningPolicyId);

}
