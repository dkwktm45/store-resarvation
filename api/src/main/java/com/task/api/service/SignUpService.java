package com.task.api.service;

import com.task.api.dto.CreateUser;
import com.task.common.exception.CustomException;
import com.task.common.exception.ErrorCode;
import com.task.domain.entity.User;
import com.task.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;

import static com.task.common.exception.ErrorCode.*;


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
            .valid(request.getValid())
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

  @Transactional
  public void validUser(String email,String code) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

    if (user.getValid()) {
      throw new CustomException(ALREADY_VERIFY);
    }else if (!user.getVerificationCode().equals(code)) {
      throw new CustomException(NOT_EQUALS_CODE);
    } else if (user.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
      throw new CustomException(EXPIRED_CODE);
    }
    user.changeValidUser();
  }
}
