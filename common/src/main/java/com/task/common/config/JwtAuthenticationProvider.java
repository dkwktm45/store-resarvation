package com.task.common.config;

import com.task.common.dto.UserVo;
import com.task.common.util.Aes256Util;
import com.task.domain.type.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtAuthenticationProvider {
  private final String secretKey = "secretKey";

  private final long tokenValidTime = 1000L * 60 * 60 * 24;

  public String createToken(Long userPk, String email, UserType userType) {
    Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(email))
        .setId(Aes256Util.encrypt(userPk.toString()));

    claims.put("roles", userType);
    Date now = new Date();
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + tokenValidTime))
        .signWith(SignatureAlgorithm.HS256,secretKey)
        .compact();
  }

  public boolean validationToken(String token) {
    try{
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey)
          .parseClaimsJws(token);
      return !claimsJws.getBody().getExpiration().before(new Date());
    }catch (Exception e){
      return false;
    }
  }

  public UserVo getUserVo(String token) {
    Claims c = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
        .getBody();
    return new UserVo(Long.valueOf(Aes256Util.decrypt(c.getId())),
        Aes256Util.decrypt(c.getSubject()));
  }
}
