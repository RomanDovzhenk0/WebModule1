package com.security;

import com.application.service.StudentServiceImpl;
import com.domain.entity.Student;
import com.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
   private static final String SECRET_KEY = "my-secret-key";
   private static final long EXPIRATION_TIME = 86400000L;
   private final StudentServiceImpl studentService;

   public String generateToken(Student student) {
      Map<String, Object> claims = new HashMap<>();
      return createToken(claims, student.getUsername());
   }

   private String createToken(Map<String, Object> claims, String subject) {
      Date now = new Date();
      Date expirationDate = new Date(new Date().getTime() + EXPIRATION_TIME);

      return Jwts.builder()
              .setClaims(claims)
              .setSubject(subject)
              .setIssuedAt(now)
              .setExpiration(expirationDate)
              .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
              .compact();
   }

   public void validateToken(String token) {
      try {
         final String username = getUsernameFromToken(token);
         if (! studentService.existByUsername(username) || isTokenExpired(token)) {
            throw new InvalidTokenException();
         }
      } catch (Exception exception) {
         throw new InvalidTokenException();
      }
   }

   private boolean isTokenExpired(String token) {
      final Date expiration = getExpirationDateFromToken(token);
      return expiration.before(new Date());
   }

   private Jws<Claims> getJws(String token) {
      return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.substring(7));
   }

   private Date getExpirationDateFromToken(String token) {
      return getJws(token).getBody().getExpiration();
   }

   private String getUsernameFromToken(String token) {
      return getJws(token).getBody().getSubject();
   }
}

