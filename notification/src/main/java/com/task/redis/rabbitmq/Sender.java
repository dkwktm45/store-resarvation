package com.task.redis.rabbitmq;

import com.task.redis.dto.MessageForm;

public interface Sender {

  void sendMessage(MessageForm messageForm);
}
