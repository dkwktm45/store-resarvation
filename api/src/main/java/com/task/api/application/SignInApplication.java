package com.task.api.application;

import com.task.api.dto.RequestUser;
import com.task.api.service.UserService;
import com.task.common.jwt.JwtAuthenticationProvider;
import com.task.common.jwt.RefreshTokenService;
import com.task.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@RequiredArgsConstructor
@Service
public class SignInApplication {
  private final UserService userService;
  private final JwtAuthenticationProvider provider;
  private final RefreshTokenService refreshTokenService;

  public HashMap<String, String> userLoginToken(RequestUser.Login req) {
    User user = userService.findValidCustomer(req.getEmail(), req.getPassword());

    String accessToken = provider.createToken(user.getUserId(), user.getEmail(),
        user.getUserType());
    String refreshToken = provider.createRefreshToken();

    refreshTokenService.saveTokenInfo(user.getUserId(), accessToken, refreshToken);
    HashMap<String, String> map = new HashMap<>();
    map.put("accessToken", accessToken);
    map.put("refreshToken", refreshToken);
    return map;
  }

  public void deleteToken(String accessToken) {
    refreshTokenService.removeRefreshToken(accessToken);
  }
}
