package com.task.noti.jwt;

import com.task.noti.jwt.dto.TokenUser;
import com.task.noti.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtAuthenticationProvider {
  private final String secretKey = "secretKey";

  private final long REFRESH_VALID_TIME = 1000 * 60 * 60 * 24 * 3;
  private final long ACCESS_VALID_TIME = 60 * 10000;

  /**
   * Access Token 생성 및 역할을 추가하는 메소드
   * */
  public String createToken(Long userPk, String email, String userType) {
    Claims claims = setClaims(userPk, email, userType);
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_VALID_TIME))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  /**
   * 토큰 검증
   * */
  public boolean validationToken(String token) {
    try {
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey)
          .parseClaimsJws(token);
      if (!claimsJws.getBody().getExpiration().before(new Date())) {
        return true;
      }
    } catch (Exception e) {
      log.warn("Expired Token Exception");
      return false;
    }
    return false;
  }

  /**
   * 토큰 정보 가져오기
   * */
  public TokenUser getUserVo(String token) {
    Claims c = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
        .getBody();
    return new TokenUser(Long.valueOf(Aes256Util.decrypt(c.getId())),
        Aes256Util.decrypt(c.getSubject()));
  }

  /**
   * Refress Token 생성 및 역할을 추가하는 메소드
   * */
  public String createRefreshToken(Long userPk, String email, String userType) {
    Claims claims = setClaims(userPk, email, userType);
    return Jwts
        .builder()
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_VALID_TIME))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  /**
   * 클레임 생성 메소드
   * */
  public Claims setClaims(Long userPk, String email, String userType) {
    Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(email))
        .setId(Aes256Util.encrypt(userPk.toString()));

    claims.put("roles", userType);
    return claims;
  }
}
