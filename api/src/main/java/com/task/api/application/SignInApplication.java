package com.task.api.application;

import com.task.api.dto.RequestUser;
import com.task.api.service.UserService;
import com.task.noti.jwt.JwtAuthenticationProvider;
import com.task.noti.jwt.RefreshTokenService;
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

  /**
   * 로그인시 검증된 유저 객체를 가지고 access, refresh를 발급받는 역할의 메소드
   * - saveTokenInfo : 토큰의 정보를 Redis에 저장하는 메소드
   *
   * feat : 토큰의 탈취의 위험이 있기에 짧은 만료시간의 accessToken과 긴 시간의 refressToken을 따로 발급
   * */
  public HashMap<String, String> getTokenByReq(RequestUser.Login req) {
    User user = userService.findValidCustomer(req.getEmail(), req.getPassword());

    String accessToken = provider.createToken(user.getUserId(), user.getEmail(),
        user.getUserType().name());
    String refreshToken = provider.createRefreshToken(user.getUserId(), user.getEmail(),
        user.getUserType().name());

    refreshTokenService.saveTokenInfo(user.getUserId(), accessToken, refreshToken);
    HashMap<String, String> map = new HashMap<>();
    map.put("accessToken", accessToken);
    map.put("refreshToken", refreshToken);
    return map;
  }

  /**
   * 토큰 삭제 메소드
   * */
  public void deleteToken(String accessToken) {
    refreshTokenService.removeRefreshToken(accessToken);
  }
}
