package com.task.noti.rabbitmq;

import com.task.noti.dto.MessageForm;

public interface Sender {

  void sendMessage(MessageForm messageForm);
}
