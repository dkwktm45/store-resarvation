package com.task.api.controller;

import com.task.noti.jwt.JwtAuthenticationProvider;
import com.task.noti.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController @RequiredArgsConstructor
@RequestMapping("/reservation/notification")
public class NotificationController {
  private final NotificationService notificationService;
  private final JwtAuthenticationProvider provider;

  /**
   * 알림을 위한 요청
   * */
  @PostMapping(value = "", produces = "text/event-stream")
  public ResponseEntity<SseEmitter> saveNotiUser(
      @RequestHeader("authorization") String token
  ){
    return ResponseEntity.ok(notificationService
        .setUserEmitter(provider.getUserVo(token).getEmail())
    );
  }
}
