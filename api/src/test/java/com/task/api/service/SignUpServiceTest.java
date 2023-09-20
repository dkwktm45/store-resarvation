package com.task.api.service;

import com.task.api.testObject.Helper;
import com.task.domain.entity.User;
import com.task.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@AutoConfigureDataJpa
@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {
  @Mock
  private UserRepository userRepository;


  @Mock
  private SignUpService signUpService;
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