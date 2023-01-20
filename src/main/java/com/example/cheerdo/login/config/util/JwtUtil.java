package com.example.cheerdo.login.config.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.example.cheerdo.login.config.filter.CustomAuthorizationFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


@Component
public class JwtUtil {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private final int ACCESS_TOKEN_VALIDITY = 60 * 60 * 1000; //1 hour
    private final int REFRESH_TOKEN_VALIDITY = 365 * 24 * 60 * 60 * 1000; // 1 year
    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${spring.jwt.secret}")
    private String secret;

    @Value("${spring.jwt.issuer}")
    private String issuer;
    private Algorithm algorithm = Algorithm.HMAC256(secret);
    private JWTVerifier verifier = JWT.require(algorithm).build(); // 검증기 제작

    public String generateRefreshToken(User user) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY)) //expire after 1 year
                .sign(algorithm);
    }

    public String generateAccessToken(User user) {
        return JWT.create()
                .withIssuer(issuer)
                .withClaim(AUTHORITIES_KEY
                        , user.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY)) //expire after 1 hour
                .sign(algorithm);
    }


    public boolean isTokenExpired(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getMemberIdFromAccessToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            verifier.verify(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        DecodedJWT decodedJWT = verifier.verify(token);
        String memberId = decodedJWT.getSubject();

        String[] roles = decodedJWT.getClaim(AUTHORITIES_KEY).asArray(String.class); //roles갖고오기
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });


        return new UsernamePasswordAuthenticationToken(memberId, null, authorities);
    }
}

