package com.example.cheerdo.login.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * TOKEN의 SUBJECT에 MEMBERID저장
 * JWT.io 로 통해 확인
 */
public class TokenIdUtil {
    public static String Decoder(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String access_token = authorizationHeader.substring("Bearer ".length()); //bearer 부분 짜르고 token검증
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); //전에 알고리즘으로 서명했기에 verify하기 위해서 알고리즘으로 확인 필요
        JWTVerifier verifier = JWT.require(algorithm).build(); // 검증기 제작
        DecodedJWT decodedJWT = verifier.verify(access_token); // 토큰 검증하기
        String memberId = decodedJWT.getSubject();
        return memberId;
    }

}

