package com.task.common.jwt;

import com.task.common.dto.TokenUser;
import com.task.common.exception.CustomException;
import com.task.common.exception.ErrorCode;
import com.task.common.util.Aes256Util;
import com.task.domain.type.UserType;
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

  public String createToken(Long userPk, String email, UserType userType) {
    Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(email))
        .setId(Aes256Util.encrypt(userPk.toString()));

    claims.put("roles", userType);
    Date now = new Date();
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + ACCESS_VALID_TIME))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

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

  public TokenUser getUserVo(String token) {
    Claims c = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
        .getBody();
    return new TokenUser(Long.valueOf(Aes256Util.decrypt(c.getId())),
        Aes256Util.decrypt(c.getSubject()));
  }

  public String createRefreshToken() {
    Claims claims = Jwts
        .claims();
    return Jwts
        .builder()
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_VALID_TIME))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }
}
