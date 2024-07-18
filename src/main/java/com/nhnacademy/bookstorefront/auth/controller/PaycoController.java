package com.nhnacademy.bookstorefront.auth.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.nhnacademy.bookstorefront.auth.feignclient.AuthClient;
import com.nhnacademy.bookstorefront.auth.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/payco")
public class PaycoController {
	@Value("${payco.client-id}")
	private String clientId;
	@Value("${payco.client-secret}")
	private String clientSecret;
	@Value("${payco.redirect-uri}")
	private String redirectUri;

	private final RestTemplate restTemplate;
	private final AuthService authService;
	private final AuthClient authClient;

	@GetMapping("/callback")
	public String paycoCallback(@RequestParam("code") String code,
		@RequestParam("state") String state,
		HttpServletResponse httpServletResponse) {
		String tokenUrl = "https://id.payco.com/oauth2.0/token";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId);
		body.add("client_secret", clientSecret);
		body.add("code", code);
		body.add("redirect_uri", redirectUri);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			String responseBody = response.getBody();
			String accessToken = extractAccessToken(responseBody);

			// 회원 번호 가져오기
			ResponseEntity<String> userInfoResponse = getPaycoUserInfo(accessToken);
			if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
				// userInfoResponse에서 회원 번호 추출
				String memberNumber = extractIdNo(userInfoResponse.getBody());
				authService.getTokensForPaycoUser(memberNumber, httpServletResponse);
				return "redirect:/";
			} else {
				return "redirect:/auth/login?error=" + URLEncoder.encode("로그인 실패", StandardCharsets.UTF_8);
			}
		} else {
			return "redirect:/auth/login?error=" + URLEncoder.encode("로그인 실패", StandardCharsets.UTF_8);
		}
	}

	private String extractAccessToken(String responseBody) {
		JSONObject jsonObject = new JSONObject(responseBody);
		return jsonObject.getString("access_token");
	}

	private ResponseEntity<String> getPaycoUserInfo(String accessToken) {
		String userInfoUrl = "https://apis-payco.krp.toastoven.net/payco/friends/find_member_v2.json";

		HttpHeaders headers = new HttpHeaders();
		headers.set("client_id", clientId);
		headers.set("access_token", accessToken);

		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.postForEntity(userInfoUrl, request, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			String responseBody = response.getBody();
			// JSON 파싱 및 사용자 정보 가져오기 로직 추가
			return ResponseEntity.ok(responseBody);
		} else {
			return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
		}
	}

	private String extractIdNo(String responseBody) {
		JSONObject jsonObject = new JSONObject(responseBody);
		JSONObject data = jsonObject.getJSONObject("data");
		JSONObject member = data.getJSONObject("member");
		return member.getString("idNo");
	}
}
