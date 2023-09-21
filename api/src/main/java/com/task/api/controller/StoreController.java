package com.task.api.controller;

import com.task.common.jwt.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reservation")
@RestController
@RequiredArgsConstructor
public class StoreController {

  private final RefreshTokenService refreshTokenService;
  @PostMapping("/logout")
  public void logoutRequest(
      @RequestHeader("authorization") String accessToken
  ) {
    refreshTokenService.removeRefreshToken(accessToken);
  }

}
