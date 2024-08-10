package com.nhnacademy.bookstorefront.user.controller;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.address.dto.response.GetAddressResponse;
import com.nhnacademy.bookstorefront.user.dto.request.UpdateUserInfoRequest;
import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;
import com.nhnacademy.bookstorefront.user.dto.response.GetUserGradeResponse;
import com.nhnacademy.bookstorefront.user.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author 김태환
 * UserController 는 사용자와 관련된 웹 요청을 처리하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	/**
	 * 현재 사용자의 정보 페이지를 조회합니다.
	 *
	 * @param model 모델 객체
	 * @return 사용자 정보 페이지 뷰 이름
	 */
	@GetMapping("/my-page")
	public String MyUserInfoPage(Model model) {
		ResponseEntity<GetMyUserInfoResponse> getMyUserInfoResponse = userService.getMyUserInfo();
		model.addAttribute("myUserInfo", getMyUserInfoResponse.getBody());

		model.addAttribute("roles", Objects.requireNonNull(getMyUserInfoResponse.getBody()).roles());

		ResponseEntity<BigDecimal> getMyTotalOrderPriceResponse = userService.getMyTotalOrderPrice();
		model.addAttribute("myTotalOrderPrice", getMyTotalOrderPriceResponse.getBody());

		ResponseEntity<Optional<GetAddressResponse>> getDefaultAddressResponse = userService.getDefaultAddress();
		model.addAttribute("myAddress", getDefaultAddressResponse.getBody().orElse(null));

		List<GetUserGradeResponse> userGrades = userService.getUserGrades();
		List<GetUserGradeResponse> sortedUserGrades = userGrades.stream()
			.sorted(Comparator.comparingInt(grade -> switch (grade.userGradeName()) {
				case "REGULAR" -> 1;
				case "ROYAL" -> 2;
				case "GRAND" -> 3;
				case "PRESTIGE" -> 4;
				default -> Integer.MAX_VALUE;
			}))
			.toList();

		model.addAttribute("userGrades", sortedUserGrades);

		return "user/my-page";
	}

	/**
	 * 사용자의 이메일로 활성화 이메일을 전송합니다.
	 *
	 * @param email 활성화 이메일을 전송할 사용자의 이메일 주소
	 * @return 빈 본문을 가진 {@link ResponseEntity} 객체. 이메일 전송 성공 여부는 상태 코드로 반환됩니다.
	 */
	@PostMapping("/send-email/dormant-to-active")
	public ResponseEntity<Void> sendEmailDormantToActive(@RequestParam String email) {
		return userService.sendEmailDormantToActive(email);
	}

	/**
	 * 사용자의 이메일과 인증 코드를 사용하여 이메일을 활성화합니다.
	 *
	 * @param email 사용자의 이메일 주소
	 * @param certifyCode 인증 코드
	 * @return 빈 본문을 가진 {@link ResponseEntity} 객체. 인증 결과는 상태 코드로 반환됩니다.
	 */
	@GetMapping("/check-email/dormant-to-active")
	public ResponseEntity<Void> checkEmailDormantToActive(@RequestParam String email,
		@RequestParam String certifyCode) {
		return userService.checkEmailDormantToActive(email, certifyCode);
	}

	/**
	 * 이메일 인증 페이지를 반환합니다.
	 *
	 * @return 이메일 인증 페이지 뷰 이름
	 */
	@GetMapping("/dormant-certify")
	public String dormantCertifyPage() {
		return "user/dormant-certify";
	}

	/**
	 * 사용자의 계정을 탈퇴 처리합니다.
	 *
	 * @param response HTTP 응답 객체. 쿠키 무효화를 위해 사용됩니다.
	 * @return 빈 본문을 가진 {@link ResponseEntity} 객체. 탈퇴 성공 여부는 상태 코드로 반환됩니다.
	 */
	@PatchMapping("/withdraw")
	public ResponseEntity<Void> withdrawUser(HttpServletResponse response) {
		ResponseEntity<Void> withdrawUserResponse = userService.withdrawUser(response);
		revokeToken(response, "Authorization");
		revokeToken(response, "Refresh-Token");
		return withdrawUserResponse;
	}

	/**
	 * 지정된 쿠키 이름으로 쿠키를 무효화합니다.
	 *
	 * @param response HTTP 응답 객체
	 * @param cookieName 무효화할 쿠키의 이름
	 */
	private void revokeToken(HttpServletResponse response, String cookieName) {
		Cookie revokedTokenCookie = new Cookie(cookieName, "");
		revokedTokenCookie.setHttpOnly(true);
		revokedTokenCookie.setMaxAge(0);
		revokedTokenCookie.setPath("/");
		response.addCookie(revokedTokenCookie);
	}

	/**
	 * 사용자 정보 업데이트 페이지를 반환합니다.
	 *
	 * @return 사용자 정보 업데이트 페이지를 표시하는 {@link ModelAndView} 객체
	 */
	@GetMapping("/update-info")
	public ModelAndView updateInfo() {
		ResponseEntity<GetMyUserInfoResponse> getMyUserInfoResponse = userService.getMyUserInfo();
		ModelAndView modelAndView = new ModelAndView("user/update-info");
		modelAndView.addObject("myUserInfo", getMyUserInfoResponse.getBody());
		return modelAndView;
	}

	/**
	 * 사용자 정보 업데이트 요청을 처리합니다.
	 *
	 * @param updateUserInfoRequest 사용자 정보 업데이트 요청 정보가 포함된 {@link UpdateUserInfoRequest} DTO
	 * @return 사용자 정보 페이지로 리다이렉트하는 {@link ModelAndView} 객체
	 */
	@PostMapping("/update-info")
	public ModelAndView updateInfoPost(@ModelAttribute UpdateUserInfoRequest updateUserInfoRequest) {
		userService.updateUserInfo(
			new UpdateUserInfoRequest(updateUserInfoRequest.name(), null, updateUserInfoRequest.birth(),
				updateUserInfoRequest.contact()));
		return new ModelAndView("redirect:/users/my-page");
	}
}
