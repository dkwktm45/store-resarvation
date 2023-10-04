package com.task.redis.rabbitmq.senderType;

import com.task.redis.dto.MessageForm;
import com.task.redis.rabbitmq.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SenderPartner implements Sender {

  private final RabbitTemplate rabbitTemplate;
  @Override
  public void sendMessage(MessageForm messageForm) {
    rabbitTemplate.convertAndSend("partner"
        ,"comment", messageForm);
  }
}
