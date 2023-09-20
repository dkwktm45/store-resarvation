package com.task.api.mailgun;

import feign.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun",url = "https://api.mailgun.net/v3/")
@Qualifier("mailgun")
public interface MailgunClient {
  @PostMapping(value =  "sandbox5bb2e0d7f68641a394d26bea137916af.mailgun.org/messages"
      , produces = "application/json")
  Response sendMail(@SpringQueryMap SendMailgun form);
}
