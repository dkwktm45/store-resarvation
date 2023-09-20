package com.task.api.controller;

import com.task.api.application.SignInApplication;
import com.task.api.dto.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignInController {

  private final SignInApplication signInApplication;

  @PostMapping("/login")
  public void loginRequest(
      @RequestBody RequestUser.Login req
  ){
    signInApplication.userLoginToken(req);
  }
}
