package com.task.api.service;

import com.task.api.testObject.Helper;
import com.task.common.exception.CustomException;
import com.task.domain.entity.User;
import com.task.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.task.common.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {
  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private SignUpService signUpService;
  @Test
  void validUserChange_SUCCESS() {
    // given
    User user = Helper.createUser();
    when(userRepository.findByEmail(user.getEmail()))
        .thenReturn(Optional.ofNullable(user));
    // when
    signUpService.validUser(user.getEmail(), user.getVerificationCode());
    // then
    verify(userRepository, times(1)).findByEmail(user.getEmail());
  }
  @Test
  void validUserChange_FAIL_user() {
    // given
    User user = Helper.createUser();
    when(userRepository.findByEmail(user.getEmail()))
        .thenReturn(Optional.empty());
    // when
    CustomException exception = assertThrows(CustomException.class,
        () -> signUpService.validUser(user.getEmail(),
            user.getVerificationCode()));
    // then
    assertEquals(NOT_FOUND_USER, exception.getErrorCode());
    verify(userRepository, times(1)).findByEmail(user.getEmail());
  }
  @Test
  void validUserChange_FAIL_validate() {
    // given
    User user = Helper.createUserValidate();
    when(userRepository.findByEmail(user.getEmail()))
        .thenReturn(Optional.of(user));
    // when
    CustomException exception = assertThrows(CustomException.class,
        () -> signUpService.validUser(user.getEmail(),
            user.getVerificationCode()));
    // then
    assertEquals(ALREADY_VERIFY, exception.getErrorCode());
    verify(userRepository, times(1)).findByEmail(user.getEmail());
  }

  @Test
  void validUserChange_FAIL_date() {
    // given
    User user = Helper.createUserValidateDay();
    when(userRepository.findByEmail(user.getEmail()))
        .thenReturn(Optional.of(user));
    // when
    CustomException exception = assertThrows(CustomException.class,
        () -> signUpService.validUser(user.getEmail(),
            user.getVerificationCode()));
    // then
    assertEquals(EXPIRED_CODE, exception.getErrorCode());
    verify(userRepository, times(1)).findByEmail(user.getEmail());
  }

  @Test
  void validUserChange_FAIL_code() {
    // given
    User user = Helper.createUser();
    when(userRepository.findByEmail(user.getEmail()))
        .thenReturn(Optional.of(user));
    // when
    CustomException exception = assertThrows(CustomException.class,
        () -> signUpService.validUser(user.getEmail(),
            "1234444"));
    // then
    assertEquals(NOT_EQUALS_CODE, exception.getErrorCode());
    verify(userRepository, times(1)).findByEmail(user.getEmail());
  }
  @Test
  void createUser() {
    User user = Helper.createUser();

    when(userRepository.save(any()))
        .thenReturn(user);
    User userResponse = signUpService.createUser(Helper.createUserRequest());

    assertEquals(userResponse.getUserName(), user.getUserName());
    assertEquals(userResponse.getUserType(), user.getUserType());
    assertEquals(userResponse.getPassword(), user.getPassword());
  }

  @Test
  void isEmailExist_FALSE() {
    String email = "wpekdl153@gmail.com";
    when(userRepository.findByEmail(anyString()))
        .thenReturn(Optional.empty());
    boolean response = signUpService.isEmailExist(email);

    assertEquals(response,false);
  }
  @Test
  void isEmailExist_True() {
    // given
    User user = Helper.createUser();
    when(userRepository.findByEmail(anyString()))
        .thenReturn(Optional.of(user));

    // when
    boolean response = signUpService.isEmailExist(user.getEmail());

    // then
    assertEquals(response,true);
  }
  // todo : changeUserValidateEmail Transaction 테스트 하기 -> 통합테스트!
}