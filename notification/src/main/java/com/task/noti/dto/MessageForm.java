package com.task.noti.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageForm {

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
