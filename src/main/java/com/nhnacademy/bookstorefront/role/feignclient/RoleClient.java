package com.nhnacademy.bookstorefront.role.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nhnacademy.bookstorefront.global.config.FeignClientConfig;
import com.nhnacademy.bookstorefront.role.dto.response.GetRoleResponse;
import com.nhnacademy.bookstorefront.role.dto.response.GetUserRoleResponse;

@FeignClient(name = "role-feign-client", url = "http://localhost:8090", configuration = FeignClientConfig.class)
public interface RoleClient {
	@GetMapping("/api/roles")
	ResponseEntity<List<GetRoleResponse>> getRoles();

	@GetMapping("/api/user-roles/user/{userId}")
	ResponseEntity<GetUserRoleResponse> getUserRoles(@PathVariable Long userId);
}
