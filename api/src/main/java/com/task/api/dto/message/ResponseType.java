package com.task.api.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResponseType {

  JOIN_USER("회원가입에 성공 했지만 이메일을 통해 인증을 부탁드립니다."),
  JOIN_ADMIN("회원가입에 성공하셨습니다.");

  private String message;

}
