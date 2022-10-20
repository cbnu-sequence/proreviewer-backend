package com.sequence.proreviewer.auth.application.util;

import static java.lang.Long.parseLong;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final Environment env;

    public String accessToken(String userId) {
        final String jwtSecret = env.getProperty("spring.jwt.secret");
        final long tokenExpiredTime = parseLong(env.getProperty("spring.jwt.expiredTime"));

        Claims claims = Jwts.claims().setSubject(userId);
        Date now = new Date();

        return Jwts
            .builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenExpiredTime))
            .signWith(SignatureAlgorithm.HS256, jwtSecret)
            .compact();
    }
}
