package com.task.api.config;

import com.task.common.client.RedisClient;
import com.task.common.dto.RefreshToken;
import com.task.common.exception.CustomException;
import com.task.common.jwt.JwtAuthenticationProvider;
import com.task.domain.entity.User;
import com.task.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.task.common.exception.ErrorCode.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {
  private final JwtAuthenticationProvider provider;
  private final RedisClient redisClient;
  private final UserRepository userRepository;
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;

    if (((HttpServletRequest) request).getRequestURI().startsWith("/store")) {
      String token = req.getHeader("authorization");
      if (token != null) {
        log.info("filter do auth");
        if (!provider.validationToken(token)) {
          RefreshToken refreshUser = redisClient.get(token,
              RefreshToken.class);
          String refreshToken = refreshUser.getRefreshToken();

          if (!provider.validationToken(refreshToken)) {
            User user = userRepository.findById(Long.valueOf(refreshUser.getId()))
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

            String accessToken =
                provider.createToken(user.getUserId(), user.getEmail(), user.getUserType());

            redisClient.delete(refreshUser.getAccessToken());
            redisClient.put(accessToken, new RefreshToken(String.valueOf(user.getUserId()),
                accessToken, refreshToken));
            HttpServletResponse res = (HttpServletResponse) response;

            res.setHeader("authorization", accessToken);
          } else {
            throw new CustomException(EXPIRED_CODE);
          }
        }
      } else {
        throw new CustomException(NOT_FOUND_TOKEN);
      }
    }
    chain.doFilter(request,response);
  }
}
