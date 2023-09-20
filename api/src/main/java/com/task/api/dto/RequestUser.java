package com.task.api.dto;

import lombok.*;

public class RequestUser {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Login{
    private String email;
    private String password;
  }
}
