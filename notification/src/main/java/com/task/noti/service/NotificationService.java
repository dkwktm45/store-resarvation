package com.task.noti.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
  private final Long EMITTER_TIMEOUT = 1000 * 6000 * 15L;
  private final Map<String, SseEmitter> connectUser = new HashMap<>();
  private final SseEmitter sseEmitter = new SseEmitter(EMITTER_TIMEOUT);

  /**
   * Application이 실행될 동안 해당 유저의 Session을 보관하는 역할의 메소드
   * */
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

  /**
   * 해당 Sesstion기반의 유저에게 알림이 가는 메소드
   * */
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
