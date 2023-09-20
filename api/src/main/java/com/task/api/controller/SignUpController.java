package com.task.api.controller;

import com.task.api.application.SignUpApplication;
import com.task.api.dto.CreateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SignUpController {

  private final SignUpApplication signUpApplication;

  @PostMapping("/join")
  public ResponseEntity<String> joinRequest(
      @RequestBody @Valid CreateUser.Request request
      ) {
    return ResponseEntity.ok(signUpApplication.signUpUser(request));
  }
}
