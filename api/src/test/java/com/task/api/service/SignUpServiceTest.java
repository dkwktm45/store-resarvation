package com.task.api.service;

import com.task.api.testObject.Helper;
import com.task.common.exception.CustomException;
import com.task.common.exception.ErrorCode;
import com.task.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static com.task.common.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {
  @Mock
  private UserService userService;
  @InjectMocks
  private SignUpService signUpService;
  @Test
  void validUserChange_SUCCESS() {
    // given
    User user = Helper.createUser();
    when(userService.findByEmail(user.getEmail()))
        .thenReturn(user);
    // when
    signUpService.validUser(user.getEmail(), user.getVerificationCode());
    // then
    verify(userService, times(1)).findByEmail(user.getEmail());
  }
  @Test
  void validUserChange_FAIL_user() {
    // given
    User user = Helper.createUser();
    when(userService.findByEmail(user.getEmail()))
        .thenThrow(new CustomException(ErrorCode.NOT_FOUND_USER));
    // when
    CustomException exception = assertThrows(CustomException.class,
        () -> signUpService.validUser(user.getEmail(),
            user.getVerificationCode()));
    // then
    assertEquals(NOT_FOUND_USER, exception.getErrorCode());
    verify(userService, times(1)).findByEmail(user.getEmail());
  }
  @Test
  void validUserChange_FAIL_validate() {
    // given
    User user = Helper.createUserValidate();
    when(userService.findByEmail(user.getEmail()))
        .thenReturn(user);
    // when
    CustomException exception = assertThrows(CustomException.class,
        () -> signUpService.validUser(user.getEmail(),
            user.getVerificationCode()));
    // then
    assertEquals(ALREADY_VERIFY, exception.getErrorCode());
    verify(userService, times(1)).findByEmail(user.getEmail());
  }

  @Test
  void validUserChange_FAIL_date() {
    // given
    User user = Helper.createUserValidateDay();
    when(userService.findByEmail(user.getEmail()))
        .thenReturn(user);
    // when
    CustomException exception = assertThrows(CustomException.class,
        () -> signUpService.validUser(user.getEmail(),
            user.getVerificationCode()));
    // then
    assertEquals(EXPIRED_CODE, exception.getErrorCode());
    verify(userService, times(1)).findByEmail(user.getEmail());
  }

  @Test
  void validUserChange_FAIL_code() {
    // given
    User user = Helper.createUser();
    when(userService.findByEmail(user.getEmail()))
        .thenReturn(user);
    // when
    CustomException exception = assertThrows(CustomException.class,
        () -> signUpService.validUser(user.getEmail(),
            "1234444"));
    // then
    assertEquals(NOT_EQUALS_CODE, exception.getErrorCode());
    verify(userService, times(1)).findByEmail(user.getEmail());
  }
  @Test
  void createUser() {
    User user = Helper.createUser();

    when(userService.saveUser(any()))
        .thenReturn(user);
    User userResponse = signUpService.createUser(Helper.createUserRequest());

    assertEquals(userResponse.getUserName(), user.getUserName());
    assertEquals(userResponse.getUserType(), user.getUserType());
    assertEquals(userResponse.getPassword(), user.getPassword());
  }

  @Test
  void isEmailExist_FALSE() {
    String email = "wpekdl153@gmail.com";
    when(userService.findByEmail(anyString()))
        .thenThrow(new CustomException(ErrorCode.NOT_FOUND_USER));

    CustomException exception = assertThrows(CustomException.class,
        () -> signUpService.isEmailExist(email));
    // then
    assertEquals(NOT_FOUND_USER, exception.getErrorCode());
    verify(userService, times(1)).findByEmail(email);
  }
  @Test
  void isEmailExist_True() {
    // given
    User user = Helper.createUser();
    when(userService.findByEmail(anyString()))
        .thenReturn(user);

    // when
    boolean response = signUpService.isEmailExist(user.getEmail());

    // then
    assertEquals(response,true);
  }
  // todo : changeUserValidateEmail Transaction 테스트 하기 -> 통합테스트!
}