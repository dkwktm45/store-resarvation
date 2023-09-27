package com.task.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  NOT_FOUND_ACCOUNT(BAD_REQUEST,"이미 가입된 회원입니다."),
  NOT_COLLECT_ACCOUNT(BAD_REQUEST,"이메일 비밀번호가 일치하지 않습니다."),
  NOT_FOUND_USER(BAD_REQUEST,"매칭되는 회원이 없습니다."),
  NOT_VALID_JOIN(BAD_REQUEST,"잘못된 인증 경로입니다."),
  ALREADY_VERIFY(BAD_REQUEST,"이미 인증이 완료됐습니다."),

  EXPIRED_TOKEN(BAD_REQUEST,"인증 시간이 만료 됐습니다."),
  NOT_EQUALS_CODE(BAD_REQUEST,"잘못된 인증 시도입니다."),
  NOT_FOUND_TOKEN(BAD_REQUEST,"토큰을 가지고 있지 않는 사용자입니다."),
  NOT_VALID_ACCOUNT(BAD_REQUEST,"인증되지 않은 회원입니다."),
  LOGIN_CHECK_VALID(BAD_REQUEST,"아이디와 패스워드를 확인해주세요."),
  EXPIRED_CODE(BAD_REQUEST, "인증 기간이 지났습니다." ),
  STORE_REG_ONE(BAD_REQUEST,"파트너 등록은 하나씩 해야합니다."),

  // 스토어
  STORE_EXIST(BAD_REQUEST, "이미 존재하는 상호명입니다."),
  STORE_NOT_FOUND(BAD_REQUEST, "존재하지 않는 상호명입니다."),

  // 예약
  RESERVATION_NOT_FOUND(BAD_REQUEST, "존재하지 않는 예약입니다."),
  RESERVATION_REFUSE(BAD_REQUEST, "거절된 예약입니다."),
  RESERVATION_NOT_VALID(BAD_REQUEST, "예약코드가 일치하지 않습니다."),
  RESERVATION_NOT_TIME(BAD_REQUEST, "예약시간이 지났습니다."),

  // 파트너
  PARTNER_NOT_EXIST(BAD_REQUEST, "유요한 파트너가 아닙니다."),

  // 잘못된 인자,
  REQUEST_BAD(BAD_REQUEST,"잘못된 인자가 들어왔습니다.");

  private final HttpStatus httpStatus;
  private final String detail;
}
