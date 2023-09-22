package com.task.api.application;

import com.task.api.dto.RequestUser;
import com.task.api.service.UserService;
import com.task.api.testObject.Helper;
import com.task.common.jwt.JwtAuthenticationProvider;
import com.task.common.jwt.RefreshTokenService;
import com.task.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignInApplicationTest {

  @Mock
  private UserService userService;

  @Mock
  private JwtAuthenticationProvider provider;

  @Mock
  private RefreshTokenService refreshTokenService;

  @InjectMocks
  private SignInApplication signInApplication;
  @Test
  void userLoginToken_success() {
    // given
    HashMap<String, String> map = new HashMap<>();
    map.put("accessToken", "testAccessToken");
    map.put("refreshToken", "testRefreshToken");
    User user = Helper.createUser();
    RequestUser.Login req = Helper.createLoginUser();
    when(userService.findValidCustomer(user.getEmail(), user.getPassword()))
        .thenReturn(user);
    when(provider.createToken(user.getUserId(),user.getEmail(),user.getUserType()))
        .thenReturn("testAccessToken");
    when(provider.createRefreshToken())
        .thenReturn("testRefreshToken");

    // when
    HashMap<String ,String > result = signInApplication.userLoginToken(req);

    // then
    assertEquals(result.get("accessToken"),map.get("accessToken"));
    assertEquals(result.get("refreshToken"),map.get("refreshToken"));
  }


  @Test
  void deleteToken() {
    String accessToken =  "testAccessToken";

    // given
    doNothing().when(refreshTokenService).removeRefreshToken(accessToken);

    // when
    signInApplication.deleteToken(accessToken);

    // then
    verify(refreshTokenService, timeout(1)).removeRefreshToken(accessToken);
  }
}