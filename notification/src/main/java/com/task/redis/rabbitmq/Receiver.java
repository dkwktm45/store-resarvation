package com.task.redis.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.redis.dto.MessageForm;
import com.task.redis.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static org.springframework.amqp.core.ExchangeTypes.DIRECT;

@RequiredArgsConstructor
@Service
public class Receiver {
  private final NotificationService notificationService;
  private final ObjectMapper objectMapper;
  @RabbitListener(
      bindings =
      @QueueBinding(
          value = @Queue,
          exchange = @Exchange(value = "user", type = DIRECT),
          key = "comment"
  ))
  public void userReceive(MessageForm form){
    notificationService.toUserMessage(form.getEmail(), String.valueOf(form.getMessage()));
  }

  @RabbitListener(
      bindings =
      @QueueBinding(
          value = @Queue,
          exchange = @Exchange(value = "partner", type = DIRECT),
          key = "comment"
      ))
  public void partnerReceive(MessageForm form){
    notificationService.toUserMessage(form.getEmail(), String.valueOf(form.getMessage()));
  }
}
