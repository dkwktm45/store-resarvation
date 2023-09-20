package com.task.api.application;

import com.task.api.dto.RequestUser;
import com.task.api.service.UserService;
import com.task.common.config.JwtAuthenticationProvider;
import com.task.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class SignInApplication {
  private final UserService userService;
  private final JwtAuthenticationProvider provider;
  public String userLoginToken(RequestUser.Login req) {
    User user = userService.findValidCustomer(req.getEmail(), req.getPassword());
    return provider.createToken(user.getUserId(),user.getEmail(),
        user.getUserType());
  }
}
