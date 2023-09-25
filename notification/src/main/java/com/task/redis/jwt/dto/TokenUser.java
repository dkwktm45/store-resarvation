package com.task.redis.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenUser {

  Long id;
  String email;

}
