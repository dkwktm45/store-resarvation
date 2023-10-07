package com.task.api.controller;

import com.task.api.application.SignUpApplication;
import com.task.api.dto.CreateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/join")
public class SignUpController {

  private final SignUpApplication signUpApplication;

  /**
   * 회원가입 요청
   * */
  @PostMapping("")
  public ResponseEntity<String> joinRequest(
      @RequestBody @Valid CreateUser.Request request
      ) {
    return ResponseEntity.ok(signUpApplication.signUpUser(request));
  }
  /**
   * 가입 인증 요청
   * */
  @GetMapping("/validate")
  public ResponseEntity<String> validRequest(
      @RequestParam String email,
      @RequestParam String code
  ) {
    return ResponseEntity.ok(signUpApplication.validCode(email, code));
  }

}
