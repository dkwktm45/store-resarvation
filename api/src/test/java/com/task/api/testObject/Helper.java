package com.task.api.testObject;

import com.task.api.dto.CreatePartner;
import com.task.api.dto.CreateUser;
import com.task.api.dto.RequestUser;
import com.task.domain.entity.User;
import com.task.domain.type.UserType;

import java.time.LocalDateTime;

public class Helper {
  public static CreateUser.Request createUserRequest() {
    return CreateUser.Request.builder()
        .userType(UserType.USER)
        .password("1")
        .userName("11")
        .email("11@naver.com").build();
  }

  public static CreateUser.Request createAdminRequest() {
    return CreateUser.Request.builder()
        .userType(UserType.USER)
        .password("1")
        .userName("11")
        .email("11@naver.com").build();
  }
  public static User createUser() {
    return User.builder()
        .userType(UserType.USER)
        .password("1")
        .verificationCode("1234")
        .verifyExpiredAt(LocalDateTime.now().plusDays(1))
        .valid(false)
        .userName("11")
        .email("11@naver.com").build();
  }
  public static User createUserValidate() {
    return User.builder()
        .userType(UserType.USER)
        .password("1")
        .verificationCode("1234")
        .verifyExpiredAt(LocalDateTime.now().plusDays(1))
        .valid(true)
        .userName("11")
        .email("11@naver.com").build();
  }

  public static RequestUser.Login createLoginUser() {
    return RequestUser.Login
        .builder().email("11@naver.com")
        .password("1").build();
  }

  public static User createUserValidateDay() {
    return User.builder()
        .userType(UserType.USER)
        .password("1")
        .verificationCode("1234")
        .verifyExpiredAt(LocalDateTime.now())
        .valid(false)
        .userName("11")
        .email("11@naver.com").build();
  }

  public static CreatePartner.Request createPartnerRequest() {
    return CreatePartner.Request.builder()
        .partnerName("11")
        .email("11@naver.com").build();
  }
}
