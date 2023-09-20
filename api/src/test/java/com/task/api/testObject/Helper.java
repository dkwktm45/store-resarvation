package com.task.api.testObject;

import com.task.api.dto.CreateUser;
import com.task.domain.entity.User;
import com.task.domain.type.UserType;

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
        .userName("11")
        .email("11@naver.com").build();
  }
}
