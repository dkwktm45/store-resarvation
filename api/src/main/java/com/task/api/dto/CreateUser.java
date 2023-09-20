package com.task.api.dto;

import com.task.common.valid.ValidEnum;
import com.task.domain.type.UserType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CreateUser {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request{
    @NotBlank(message = "이름은 필수로 입력해야합니다.")
    private String userName;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수로 입력 값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수로 입력 값입니다.")
    private String password;
    private Boolean valid;

    @ValidEnum(enumClass = UserType.class,message = "UserType이 아닙니다.")
    private UserType userType;
  }
}
