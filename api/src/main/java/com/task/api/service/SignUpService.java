package com.task.api.service;

import com.task.api.dto.CreateUser;
import com.task.domain.entity.User;
import com.task.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;


@Service
@RequiredArgsConstructor
public class SignUpService {

  private final UserRepository userRepository;

  @Transactional
  public User createUser(CreateUser.Request request) {
    return userRepository.save(
        User.builder()
            .userName(request.getUserName())
            .userType(request.getUserType())
            .email(request.getEmail())
            .password(request.getPassword()).build()
    );
  }

  public boolean isEmailExist(String email) {
    return userRepository.findByEmail(email.toLowerCase(Locale.ROOT))
        .isPresent();
  }
  @Transactional
  public void changeUserValidateEmail(
      User user,
      String verificationCode) {
    user.createValid(verificationCode, LocalDateTime.now());
  }
}
