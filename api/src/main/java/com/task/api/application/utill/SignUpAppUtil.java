package com.task.api.application.utill;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SignUpAppUtil {
  private final StringBuilder builder = new StringBuilder();

  /**
   * 랜덤 code 생성 메소드
   * */
  public String getRandomCode() {
    return UUID.randomUUID().toString().substring(0, 5);
  }
  /**
   * email 보내기 위한 String 생성 메소드
   * */
  public String getVerificationEmailBody(String email, String name,
                                          String code) {

    return builder.append("Hello")
        .append(name).append("! Please click link for verification.")
        .append("http://localhost:8080/join/validate?email" +
            "=").append(email).append("&code=").append(code).toString();
  }
}
