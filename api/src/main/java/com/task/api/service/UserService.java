package com.task.api.service;

import com.task.common.exception.CustomException;
import com.task.domain.entity.User;
import com.task.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.task.common.exception.ErrorCode.NOT_COLLECT_ACCOUNT;
import static com.task.common.exception.ErrorCode.NOT_VALID_ACCOUNT;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public User findValidCustomer(String email, String password) {
    User user = userRepository.findByEmailAndPassword(email, password)
        .orElseThrow(() -> new CustomException(NOT_COLLECT_ACCOUNT));

    if (user.getValid()) {
      throw new CustomException(NOT_VALID_ACCOUNT);
    }
    return user;
  }
}
