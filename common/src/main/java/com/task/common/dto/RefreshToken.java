package com.task.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {
  @Id
  private String id;

  private String refreshToken;

  private String accessToken;
}
