package com.task.noti.rabbitmq;

import com.task.noti.dto.MessageForm;
import com.task.noti.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static org.springframework.amqp.core.ExchangeTypes.DIRECT;

@RequiredArgsConstructor
@Service
public class Receiver {
  private final NotificationService notificationService;

  /**
   * 유저에게 알림이 가는 메소드
   * */
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
  /**
   * 파트너에게 알림이 가는 메소드
   * */
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
