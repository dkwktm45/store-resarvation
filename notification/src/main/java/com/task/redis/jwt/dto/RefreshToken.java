package com.task.redis.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {
  private String id;

  private String refreshToken;

  private String accessToken;
}
