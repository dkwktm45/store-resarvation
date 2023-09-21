package com.task.api.controller;

import com.task.api.application.SignInApplication;
import com.task.api.dto.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class SignInController {

  private final SignInApplication signInApplication;

  @PostMapping("/login")
  public ResponseEntity<HashMap<String, String>> loginRequest(
      @RequestBody RequestUser.Login req
  ){
    return ResponseEntity.ok(signInApplication.userLoginToken(req));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logoutRequest(
      @RequestHeader("AUTH-TOKEN") String accessToken
  ) {
    signInApplication.deleteToken(accessToken);
    return ResponseEntity.ok().build();
  }
}