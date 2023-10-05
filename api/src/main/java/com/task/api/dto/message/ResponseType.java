package com.task.api.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 각종 void 타입의 메소드를 성공시 반환하는 enum class
 * */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResponseType {

  JOIN_USER("회원가입에 성공 했지만 이메일을 통해 인증을 부탁드립니다."),
  SUCCESS_MESSAGE("예약이 원활하게 진행됐습니다. 2시간 이내로 도착 부탁드립니다."),
  CONTINUE_MESSAGE("예약이 가능한지 묻고 오겠습니다. 잠시만 기다려주세요."),
  ADMISSION_IS_POSSIBLE("지금 입장 가능하니 직원에게 문의해주세요."),
  WRITE_SUCCESS_MESSAGE("리뷰 작성이 완료됐습니다."),
  ADMISSION_IS_IMPOSSIBLE("지금 입장은 불가능 하니 직원에게 문의해주세요"),
  JOIN_ADMIN("회원가입에 성공하셨습니다.");

  private String message;

}
