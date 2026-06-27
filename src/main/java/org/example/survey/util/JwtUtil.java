package org.example.survey.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private Long expire;

    // 生成签名密钥
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 生成token
    public String generateToken(Long userId, String userName) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expire * 1000);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", userName)
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(getSecretKey())
                .compact();
    }

    // 解析token
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getUserId(String token) {
        Claims claims = parseToken(token);
        return Long.valueOf(claims.getSubject());
    }

    public String getUserName(String token) {
        Claims claims = parseToken(token);
        return claims.get("username", String.class);
    }

    // 判断token是否有效
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
