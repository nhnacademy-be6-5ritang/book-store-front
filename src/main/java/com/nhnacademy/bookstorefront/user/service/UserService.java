package com.nhnacademy.bookstorefront.user.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.address.dto.response.GetAddressResponse;
import com.nhnacademy.bookstorefront.user.dto.request.UpdateUserInfoRequest;
import com.nhnacademy.bookstorefront.user.dto.request.UpdateUserRoleRequest;
import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;
import com.nhnacademy.bookstorefront.user.dto.response.GetUserInfoResponse;
import com.nhnacademy.bookstorefront.user.dto.response.UpdateUserInfoResponse;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author 김태환
 * 사용자와 관련된 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 */
public interface UserService {

	/**
	 * 현재 사용자의 정보를 조회합니다.
	 *
	 * @return 현재 사용자의 정보를 포함하는 {@link ResponseEntity} 객체. 본문에는 {@link GetMyUserInfoResponse} DTO가 포함됩니다.
	 */
	ResponseEntity<GetMyUserInfoResponse> getMyUserInfo();

	/**
	 * 현재 사용자의 계정을 탈퇴 처리합니다.
	 *
	 * @param response HTTP 응답 객체. 사용자의 탈퇴 상태를 반영합니다.
	 * @return 빈 본문을 가진 {@link ResponseEntity} 객체. 탈퇴 성공 여부는 상태 코드로 반환됩니다.
	 */
	ResponseEntity<Void> withdrawUser(HttpServletResponse response);

	/**
	 * 현재 사용자의 총 주문 금액을 조회합니다.
	 *
	 * @return 현재 사용자의 총 주문 금액을 포함하는 {@link ResponseEntity} 객체. 본문에는 {@link BigDecimal} 객체가 포함됩니다.
	 */
	ResponseEntity<BigDecimal> getMyTotalOrderPrice();

	/**
	 * 사용자에게 활성화 이메일을 전송합니다.
	 *
	 * @param email 활성화 이메일을 전송할 사용자의 이메일 주소
	 * @return 빈 본문을 가진 {@link ResponseEntity} 객체. 이메일 전송 성공 여부는 상태 코드로 반환됩니다.
	 */
	ResponseEntity<Void> sendEmailDormantToActive(String email);

	/**
	 * 사용자가 입력한 인증 코드를 확인하여 이메일을 활성화합니다.
	 *
	 * @param email 사용자의 이메일 주소
	 * @param certifyCode 인증 코드
	 * @return 빈 본문을 가진 {@link ResponseEntity} 객체. 인증 결과는 상태 코드로 반환됩니다.
	 */
	ResponseEntity<Void> checkEmailDormantToActive(String email, String certifyCode);

	/**
	 * 현재 사용자의 기본 주소를 조회합니다.
	 *
	 * @return 현재 사용자의 기본 주소를 포함하는 {@link ResponseEntity} 객체. 본문에는 {@link Optional<GetAddressResponse>} 객체가 포함됩니다.
	 */
	ResponseEntity<Optional<GetAddressResponse>> getDefaultAddress();

	/**
	 * 모든 사용자의 정보를 페이지네이션하여 조회합니다.
	 *
	 * @param pageable 페이지 정보
	 * @return 페이지네이션된 사용자 정보를 포함하는 {@link ResponseEntity} 객체. 본문에는 {@link Page<GetUserInfoResponse>} 객체가 포함됩니다.
	 */
	ResponseEntity<Page<GetUserInfoResponse>> getUsers(Pageable pageable);

	/**
	 * 관리자 페이지를 조회합니다.
	 *
	 * @return 빈 본문을 가진 {@link ResponseEntity} 객체. 페이지 조회 성공 여부는 상태 코드로 반환됩니다.
	 */
	ResponseEntity<Void> getAdminPage();

	/**
	 * 사용자의 역할을 업데이트합니다.
	 *
	 * @param updateUserRoleRequest 업데이트할 사용자 역할 정보가 포함된 {@link UpdateUserRoleRequest} DTO
	 */
	void updateUserRole(UpdateUserRoleRequest updateUserRoleRequest);

	/**
	 * Payco 와 연결합니다.
	 *
	 * @param memberId Payco와 연결할 사용자의 회원 ID
	 */
	void paycoConnect(String memberId);

	/**
	 * 제공된 요청 세부 정보를 기반으로 사용자 정보를 업데이트합니다.
	 *
	 * @param updateUserInfoRequest 업데이트할 사용자 정보를 포함하는 요청 객체입니다.
	 * @return {@link UpdateUserInfoResponse} 업데이트 작업의 결과를 포함하는 응답 객체입니다.
	 */
	UpdateUserInfoResponse updateUserInfo(UpdateUserInfoRequest updateUserInfoRequest);
}
