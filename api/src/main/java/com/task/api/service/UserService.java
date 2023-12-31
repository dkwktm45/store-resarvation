package com.task.api.service;

import com.task.common.exception.CustomException;
import com.task.common.exception.ErrorCode;
import com.task.domain.entity.User;
import com.task.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.task.common.exception.ErrorCode.NOT_COLLECT_ACCOUNT;
import static com.task.common.exception.ErrorCode.NOT_VALID_ACCOUNT;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  /**
   * 검증된 유저를 반환 메소드
   * */
  public User findValidCustomer(String email, String password) {
    User user = userRepository.findByEmailAndPassword(email, password)
        .orElseThrow(() -> new CustomException(NOT_COLLECT_ACCOUNT));

    if (!user.getValid()) {
      throw new CustomException(NOT_VALID_ACCOUNT);
    }
    return user;
  }

  /**
   * 유저 ID를 통한 객체 반환
   * */
  public User getUserId(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
  }

  /**
   * 유저 객체 저장
   * */
  @Transactional
  public User saveUser(User user) {
    return userRepository.save(user);
  }
  /**
   * email을 통한 유저 객체 반환
   * */

  public User findByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
  }
}
