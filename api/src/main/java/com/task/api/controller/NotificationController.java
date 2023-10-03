package com.task.api.controller;

import com.task.redis.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController @RequiredArgsConstructor
@RequestMapping("/reservation/notification")
public class NotificationController {
  private final NotificationService notificationService;
  @PostMapping(value = "", produces = "text/event-stream")
  public ResponseEntity<SseEmitter> saveNotiUser(@RequestBody String email){
    return ResponseEntity.ok(notificationService.setUserEmitter(email));
  }
}
