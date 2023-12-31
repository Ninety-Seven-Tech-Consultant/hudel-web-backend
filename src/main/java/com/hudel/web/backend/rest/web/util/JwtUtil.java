package com.hudel.web.backend.rest.web.util;

import com.hudel.web.backend.config.properties.SysparamProperties;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

  @Autowired
  private SysparamProperties sysparamProperties;

  public String getUsernameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(sysparamProperties.getJwtSecret())
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public Boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(sysparamProperties.getJwtSecret()).parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  public ResponseCookie generateJwtCookie(UserDetails userDetails) {
    String token = generateTokenFromUsername(userDetails.getUsername());
    return ResponseCookie.from(sysparamProperties.getJwtCookieName(), token)
        .maxAge(sysparamProperties.getJwtExpirationTimeInMillis() / 1000)
        .build();
  }

  private String generateTokenFromUsername(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(
            new Date(new Date().getTime() + sysparamProperties.getJwtExpirationTimeInMillis()))
        .signWith(SignatureAlgorithm.HS512, sysparamProperties.getJwtSecret())
        .compact();
  }
}
