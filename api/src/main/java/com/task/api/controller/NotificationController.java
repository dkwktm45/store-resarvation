package com.task.api.controller;

import com.task.redis.jwt.JwtAuthenticationProvider;
import com.task.redis.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController @RequiredArgsConstructor
@RequestMapping("/reservation/notification")
public class NotificationController {
  private final NotificationService notificationService;
  private final JwtAuthenticationProvider provider;
  @PostMapping(value = "", produces = "text/event-stream")
  public ResponseEntity<SseEmitter> saveNotiUser(
      @RequestHeader("authorization") String token
  ){
    return ResponseEntity.ok(notificationService
        .setUserEmitter(provider.getUserVo(token).getEmail())
    );
  }
}
