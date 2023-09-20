package com.task.api.application;

import com.task.api.dto.CreateUser;
import com.task.api.mailgun.MailgunClient;
import com.task.api.mailgun.SendMailgun;
import com.task.api.service.SignUpService;
import com.task.common.exception.CustomException;
import com.task.domain.entity.User;
import com.task.domain.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.task.api.dto.message.ResponseType.JOIN_ADMIN;
import static com.task.api.dto.message.ResponseType.JOIN_USER;
import static com.task.common.exception.ErrorCode.NOT_FOUND_ACCOUNT;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

  private final MailgunClient mailgunClient;
  private final SignUpService signUpService;
  public String signUpUser(CreateUser.Request user){
    if (signUpService.isEmailExist(user.getEmail())) {
      throw new CustomException(NOT_FOUND_ACCOUNT);
    }
    User responseUser = signUpService.createUser(user);
    if (responseUser.getUserType().equals(UserType.USER)) {

      String code = getRandomCode();
      mailgunClient.sendMail(SendMailgun.builder()
          .from("reservatation@gmail.com")
          .to(user.getEmail())
          .subject("Verification Email")
          .text(getVerificationEmailBody(user.getEmail(),
              user.getUserName(), code))
          .build());
      signUpService.changeUserValidateEmail(responseUser, code);
      return JOIN_USER.getMessage();
    } else {
      return JOIN_ADMIN.getMessage();
    }
  }

  private String getRandomCode() {
    return UUID.randomUUID().toString().substring(0, 5);
  }
  private String getVerificationEmailBody(String email, String name,
                                          String code) {
    StringBuilder builder = new StringBuilder();

    return builder.append("Hello")
        .append(name).append("! Please click link for verification.")
        .append("http://localhost:8080/signup/user/verify?email" +
            "=").append(email).append("&code=").append(code).toString();
  }
}
