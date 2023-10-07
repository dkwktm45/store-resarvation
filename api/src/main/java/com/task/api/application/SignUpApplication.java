package com.task.api.application;

import com.task.api.application.utill.SignUpAppUtil;
import com.task.api.dto.CreateUser;
import com.task.api.mailgun.MailgunClient;
import com.task.api.mailgun.SendMailgun;
import com.task.api.service.SignUpService;
import com.task.common.exception.CustomException;
import com.task.domain.entity.User;
import com.task.domain.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.task.api.dto.message.ResponseType.JOIN_USER;
import static com.task.api.dto.message.ResponseType.VALID_SUCCESS;
import static com.task.common.exception.ErrorCode.NOT_FOUND_ACCOUNT;
import static com.task.common.exception.ErrorCode.NOT_VALID_JOIN;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

  private final SignUpAppUtil signUpAppUtil;
  private final MailgunClient mailgunClient;
  private final SignUpService signUpService;

  /**
   * CreateUser.Request매개변수를 통한 회원 가입 로직 메소드
   * - isEmailExist : 해당 이메일이 있는지를 검증
   * - signUpUtil : 각종 편의를 위한 utils
   * - sendMail : 메일을 통한 검증을 위한 메일을 1차적으로 보냄
   * - changeUserValidateEmail : 유저의 정보를 변경 -> 검증할 수 있도록
   * */
  public String signUpUser(CreateUser.Request user){
    if (signUpService.isEmailExist(user.getEmail())) {
      throw new CustomException(NOT_FOUND_ACCOUNT);
    }
    User responseUser = signUpService.createUser(user);
    if (responseUser.getUserType().equals(UserType.USER)) {
      String code = signUpAppUtil.getRandomCode();
      mailgunClient.sendMail(SendMailgun.builder()
          .from("reservatation@gmail.com")
          .to(user.getEmail())
          .subject("Verification Email")
          .text(signUpAppUtil.getVerificationEmailBody(user.getEmail(),
              user.getUserName(), code))
          .build());
      signUpService.changeUserValidateEmail(responseUser, code);
      return JOIN_USER.getMessage();
    }  else {
      throw new CustomException(NOT_VALID_JOIN);
    }
  }

  /**
   * 메일을 통한 검증 메소드
   */
  public String validCode(String email,String code) {
    signUpService.validUser(email, code);

    return VALID_SUCCESS.getMessage();
  }
}
