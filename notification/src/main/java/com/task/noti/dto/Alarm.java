package com.task.noti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class Alarm  implements Serializable {
  private static final long serialVersionUID = 6494678977089006639L;
  private String id;

  private String senderId;

  private String senderName;

  private Alarm.AlarmType alarmType;
  public enum AlarmType {
    REFUSE, AGREE , MESSAGE
  }
}
