package com.monitoringserver.security;

import com.monitoringserver.entity.UserEntity;
import com.monitoringserver.entity.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenProvider {
    private static final String SECRET_KEY = "YxgCkpqLQ3NHaslkdalkdjal1234321XcRfhiPc5isYenAR401234lasdsvzwVcqW2Mpk";
    Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String createAccessToken(String userId) {
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(24, ChronoUnit.HOURS));

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuer("CUBOX")
                .setClaims(Map.of("userId", userId))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String createRefreshToken(String userId) {
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(7, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuer("CUBOX")
                .setClaims(Map.of("userId", userId))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userId", String.class);
    }


}
