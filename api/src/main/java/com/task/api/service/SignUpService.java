package com.task.api.service;

import com.task.api.dto.CreateUser;
import com.task.common.exception.CustomException;
import com.task.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.task.common.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class SignUpService {

  private final UserService userService;

  /**
   * 유저 생성
   * */
  @Transactional
  public User createUser(CreateUser.Request request) {
    return userService.saveUser(User.builder()
        .userName(request.getUserName())
        .userType(request.getUserType())
        .valid(request.getValid())
        .email(request.getEmail())
        .password(request.getPassword()).build()
    );
  }

  /**
   * 이메일이 존재하는지 여부 메소드
   * */
  public boolean isEmailExist(String email) {
    return !Objects.isNull(userService.findByEmail(email));
  }
  /**
   * 유저의 검증을 위한 데이터 추가 메소드
   * */
  @Transactional
  public void changeUserValidateEmail(
      User user,
      String verificationCode) {
    user.createValid(verificationCode, LocalDateTime.now());
  }

  /**
   * 유저 검증 및 변환 메소드
   * */
  @Transactional
  public void validUser(String email,String code) {
    User user = userService.findByEmail(email);

    if (user.getValid()) {
      throw new CustomException(ALREADY_VERIFY);
    }else if (!user.getVerificationCode().equals(code)) {
      throw new CustomException(NOT_EQUALS_CODE);
    } else if (!user.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
      throw new CustomException(EXPIRED_CODE);
    }
    user.changeValidUser();
  }
}
