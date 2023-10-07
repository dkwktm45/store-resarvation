package com.task.api.config;

import com.task.noti.jwt.client.RedisClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@RequiredArgsConstructor
public class RedisBeanConfig {

  private final RedisTemplate<String,Object> redisTemplate;

  @Bean
  public RedisClient redisClient() {
    return new RedisClient(redisTemplate);
  }

}
