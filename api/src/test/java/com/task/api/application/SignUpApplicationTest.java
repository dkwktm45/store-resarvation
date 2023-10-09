package com.task.api.application;

import com.task.api.application.utill.SignUpAppUtil;
import com.task.api.dto.CreateUser;
import com.task.api.mailgun.MailgunClient;
import com.task.api.service.SignUpService;
import com.task.api.testObject.Helper;
import com.task.common.exception.CustomException;
import com.task.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.task.api.dto.message.ResponseType.JOIN_USER;
import static com.task.common.exception.ErrorCode.NOT_FOUND_ACCOUNT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignUpApplicationTest {

  @Mock
  private SignUpService signUpService;

  @Mock
  private SignUpAppUtil signUpAppUtil;

  @Mock
  private MailgunClient mailgunClient;

  @InjectMocks
  private SignUpApplication signUpApplication;
  @Test
  void signUpValidUser_success() {
    // given
    String email = "wwww@naver.com";
    String code = "wdawd";
    doNothing().when(signUpService).validUser(anyString(), anyString());

    // when
    String message = signUpApplication.validCode(email,code);

    // then
    verify(signUpService).validUser(anyString(), anyString());
    assertEquals(message,"인증이 성공했습니다.");
  }

  @Test
  void signUpUser() {
    // given
    CreateUser.Request req = Helper.createUserRequest();
    User user = Helper.createUser();
    String code = "1234";
    // when
    when(signUpService.isEmailExist(any()))
        .thenReturn(false);
    when(signUpService.createUser(any()))
        .thenReturn(user);
    when(signUpAppUtil.getRandomCode())
        .thenReturn(code);
    when(mailgunClient.sendMail(any())).thenReturn(null);
    doNothing().when(signUpService).changeUserValidateEmail(any(),anyString());

    String message = signUpApplication.signUpUser(req);
    // then
    verify(signUpService).isEmailExist(any());
    verify(signUpService).createUser(any());
    verify(mailgunClient).sendMail(any());
    verify(signUpAppUtil).getRandomCode();
    verify(signUpAppUtil).getVerificationEmailBody(any(), any(), any());
    verify(signUpService).changeUserValidateEmail(any(), anyString());
    assertEquals(message, JOIN_USER.getMessage());
  }

  @Test
  void signUpUser_fail() {
    // given
    CreateUser.Request req = Helper.createUserRequest();

    // when
    when(signUpService.isEmailExist(any()))
        .thenReturn(true);


    // then
    CustomException exception = assertThrows(CustomException.class,
        () ->signUpApplication.signUpUser(req));

    verify(signUpService).isEmailExist(any());
    assertEquals(exception.getErrorCode().getHttpStatus(), NOT_FOUND_ACCOUNT.getHttpStatus());
    assertEquals(exception.getErrorCode().getDetail(), NOT_FOUND_ACCOUNT.getDetail());
  }
}