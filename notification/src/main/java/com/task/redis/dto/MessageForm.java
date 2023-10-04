package com.task.redis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageForm {

  private Long id;
  private String email;
  private Message message;




  @NoArgsConstructor
  @Getter
  @AllArgsConstructor
  public enum Message{
    USER_SUCCESS("예약이 성공적으로 완료되었습니다."),
    PARTNER_RES("예약이 진행되었습니다."),
    PARTNER_REFUSE("예약을 수락하시겠습니까?");

    String value;
  }
}
