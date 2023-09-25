package com.task.redis.jwt;

import com.task.redis.jwt.client.RedisClient;
import com.task.redis.jwt.dto.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final RedisClient redisClient;
  @Transactional
  public void saveTokenInfo(Long userId, String accessToken,
                            String refreshToken) {
    redisClient.put(accessToken, RefreshToken.builder()
        .refreshToken(refreshToken)
        .accessToken(accessToken)
        .id(userId.toString()).build());
  }

  @Transactional
  public void removeRefreshToken(String accessToken) {
    redisClient.delete(accessToken);
  }
}
