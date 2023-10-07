package com.task.noti.jwt.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.common.exception.CustomException;
import com.task.noti.jwt.dto.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import static com.task.common.exception.ErrorCode.NOT_FOUND_USER;

@RequiredArgsConstructor
@Slf4j
public class RedisClient {

  private final RedisTemplate<String, Object> redisTemplate;
  private static final ObjectMapper mapper = new ObjectMapper();

  public <T> T get(String token, Class<T> classType) {
    return getObject(token, classType);
  }

  private <T> T getObject(String key, Class<T> classType) {
    String redisValue = (String) redisTemplate.opsForValue().get(key);
    if (ObjectUtils.isEmpty(redisValue)) {
      return null;
    }else {
      try{
        return mapper.readValue(redisValue, classType);
      } catch (JsonProcessingException e) {
        log.error("parsing error",e);
        return null;
      }
    }
  }

  public void put(String token, RefreshToken refreshToken) {
    putObject(token,refreshToken);
  }
  private void putObject(String key, RefreshToken refreshToken) {
    try{
      redisTemplate.opsForValue().set(key, mapper.writeValueAsString(refreshToken));
    } catch (JsonProcessingException e) {
      throw new CustomException(NOT_FOUND_USER);
    }
  }

  public void delete(String token) {
    deleteObject(token);
  }
  private void deleteObject(String key) {
    redisTemplate.delete(key);
  }
}
