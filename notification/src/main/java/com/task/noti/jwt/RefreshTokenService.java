package com.task.noti.jwt;

import com.task.noti.jwt.client.RedisClient;
import com.task.noti.jwt.dto.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final RedisClient redisClient;

  /**
   * 토큰 정보를 redisClient를 통한 저장 메소드
   * */
  @Transactional
  public void saveTokenInfo(Long userId, String accessToken,
                            String refreshToken) {
    redisClient.put(accessToken, RefreshToken.builder()
        .refreshToken(refreshToken)
        .accessToken(accessToken)
        .id(userId.toString()).build());
  }
  /**
   * 토큰 정보를 redisClient를 통한 삭제 메소드
   * */
  @Transactional
  public void removeRefreshToken(String accessToken) {
    redisClient.delete(accessToken);
  }
}
