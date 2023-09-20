package com.task.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  NOT_FOUND_ACCOUNT(HttpStatus.BAD_REQUEST,"이미 가입된 회원입니다."),
  NOT_COLLECT_ACCOUNT(HttpStatus.BAD_REQUEST,"이메일 비밀번호가 일치하지 않습니다."),
  NOT_FOUND_USER(HttpStatus.BAD_REQUEST,"매칭되는 회원이 없습니다."),
  ALREADY_VERIFY(HttpStatus.BAD_REQUEST,"이미 인증이 완료됐습니다."),

  //로그인
  EXPIRED_CODE(HttpStatus.BAD_REQUEST,"인증 시간이 만료 됐습니다."),
  NOT_EQUALS_CODE(HttpStatus.BAD_REQUEST,"잘못된 인증 시도입니다."),
  NOT_VALID_ACCOUNT(HttpStatus.BAD_REQUEST,"인증되지 않은 회원입니다."),
  LOGIN_CHECK_VALID(HttpStatus.BAD_REQUEST,"아이디와 패스워드를 확인해주세요."),

  // 잘못된 인자
  REQUEST_BAD(HttpStatus.BAD_REQUEST,"잘못된 인자가 들어왔습니다.");

  private final HttpStatus httpStatus;
  private final String detail;
}
