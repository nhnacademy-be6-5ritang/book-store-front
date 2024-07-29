package com.nhnacademy.bookstorefront.user.controller;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhnacademy.bookstorefront.address.dto.response.GetAddressResponse;
import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;
import com.nhnacademy.bookstorefront.user.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@GetMapping("/my-page")
	public String MyUserInfoPage(Model model) {
		ResponseEntity<GetMyUserInfoResponse> getMyUserInfoResponse = userService.getMyUserInfo();
		model.addAttribute("myUserInfo", getMyUserInfoResponse.getBody());

		model.addAttribute("roles", Objects.requireNonNull(getMyUserInfoResponse.getBody()).roles());

		ResponseEntity<BigDecimal> getMyTotalOrderPriceResponse = userService.getMyTotalOrderPrice();
		model.addAttribute("myTotalOrderPrice", getMyTotalOrderPriceResponse.getBody());

		ResponseEntity<Optional<GetAddressResponse>> getDefaultAddressResponse = userService.getDefaultAddress();
		model.addAttribute("myAddress", getDefaultAddressResponse.getBody().orElse(null));
		return "user/my-page";
	}

	@PostMapping("/send-email/dormant-to-active")
	public ResponseEntity<Void> sendEmailDormantToActive(@RequestParam String email) {
		return userService.sendEmailDormantToActive(email);
	}

	@GetMapping("/check-email/dormant-to-active")
	public ResponseEntity<Void> checkEmailDormantToActive(@RequestParam String email,
		@RequestParam String certifyCode) {
		return userService.checkEmailDormantToActive(email, certifyCode);
	}

	@ResponseBody
	@PatchMapping("/withdraw")
	public ResponseEntity<Void> withdrawUser(HttpServletResponse response) {
		ResponseEntity<Void> withdrawUserResponse = userService.withdrawUser(response);
		revokeToken(response, "Authorization");
		revokeToken(response, "Refresh-Token");
		return withdrawUserResponse;
	}

	private void revokeToken(HttpServletResponse response, String cookieName) {
		Cookie revokedTokenCookie = new Cookie(cookieName, "");
		revokedTokenCookie.setHttpOnly(true);
		revokedTokenCookie.setMaxAge(0);
		revokedTokenCookie.setPath("/");
		response.addCookie(revokedTokenCookie);
	}
}
