package com.task.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
  private final Long EMITTER_TIMEOUT = 1000 * 6000 * 15L;
  private final Map<String, SseEmitter> connectUser = new HashMap<>();
  private final SseEmitter sseEmitter = new SseEmitter(EMITTER_TIMEOUT);
  public SseEmitter setUserEmitter(String email) {
    if(email == null){
      throw new RuntimeException("유저 아이디가 null 입니다.");
    }
    sseEmitter.onCompletion(() -> {
      connectUser.remove(email);
    });

    sseEmitter.onTimeout(() -> {
      sseEmitter.complete();
      connectUser.remove(email);
    });
    connectUser.put(email, sseEmitter);
    return sseEmitter;
  }
  public void toUserMessage(String email, String message) {
    if (connectUser.containsKey(email)){
      try {
        connectUser.get(email).send(message, MediaType.TEXT_PLAIN);
      }catch (Exception e){
        e.printStackTrace();
      }
    }
  }
}
