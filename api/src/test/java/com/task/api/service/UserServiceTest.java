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
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private UserService userService;

  @Test
  void validCustomer_success(){
    //given
    User user = Helper.createUserValidate();
    String email = "11@naver.com";
    String password = "1";
    when(userRepository.findByEmailAndPassword(email, password))
        .thenReturn(Optional.of(user));
    //when
    User result = userService.findValidCustomer(email, password);

    //then
    assertEquals(result.getUserName(), user.getUserName());
    assertEquals(result.getPassword(), user.getPassword());
  }
  @Test
  void validCustomer_fail_validate(){
    //given
    User user = Helper.createUser();
    String email = "11@naver.com";
    String password = "1";
    when(userRepository.findByEmailAndPassword(email, password))
        .thenReturn(Optional.of(user));
    //when
    CustomException exception = assertThrows(CustomException.class,
        () -> userService.findValidCustomer(email, password));

    //then
    assertEquals(NOT_VALID_ACCOUNT, exception.getErrorCode());
    verify(userRepository, times(1)).findByEmailAndPassword(email, password);
  }

  @Test
  void validCustomer_fail_user(){
    //given
    String email = "11@naver.com";
    String password = "1";
    when(userRepository.findByEmailAndPassword(email, password))
        .thenReturn(Optional.empty());
    //when
    CustomException exception = assertThrows(CustomException.class,
        () -> userService.findValidCustomer(email, password));

    //then
    assertEquals(NOT_COLLECT_ACCOUNT, exception.getErrorCode());
    verify(userRepository, times(1)).findByEmailAndPassword(email, password);
  }
}