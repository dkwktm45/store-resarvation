package com.task.common.jwt;

import com.task.common.client.RedisClient;
import com.task.common.dto.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final RedisClient redisClient;
  @Transactional
  public void saveTokenInfo(Long userId, String refreshToken,
                            String accessToken) {
    redisClient.put(accessToken, new RefreshToken(String.valueOf(userId),
        refreshToken,
        accessToken));
  }

  @Transactional
  public void removeRefreshToken(String accessToken) {
    redisClient.delete(accessToken);
  }
}
