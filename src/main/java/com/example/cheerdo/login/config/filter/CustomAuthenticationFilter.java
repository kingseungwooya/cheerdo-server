package com.example.cheerdo.login.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * 회원 로그인 검증을 위한 filter
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final long ACCESS_TOKEN_EXPIRATION = 60 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION = 365 * 24 * 60 * 60 * 1000;

    // 추후 Redis를 이용해 키 Random Generate 하고 저장해보기
    private static final String SECRET_KEY = readSecretKeyFromFile();
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY.getBytes());
    private final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    // 인증을 수행하는 객체
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    private static String readSecretKeyFromFile() {
        return "secret";
    }

    // 사용자가 전송한 인증 정보를 추출한다.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String memberId = request.getParameter("memberId");
        String password = request.getParameter("password");

        LOGGER.info("login 한 너의 id: {} ", memberId);
        LOGGER.info("login 한 너의 비번 : {} ", password);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 추출한 인증정보를 토대로 인증을 시도한다.
        // 인증은 AuthenticationManager 와 AuthenticationProvider 를 사용하여 수행
        return authenticationManager.authenticate(authenticationToken);
    }


    // authenticationManager 에 의해 인증에 성공할 경우
    // 인증에 성공한 Authentication 을 인자로 담아 successfulAuthentication 을 호출합니다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        String access_token = createAccessToken(user, request);
        String refresh_token = createRefreshToken(user, request);

        // 클라이언트에 토큰 보내기
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    private String createAccessToken(User user, HttpServletRequest request) {
        Date expiresAt = new Date((System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION));
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiresAt)
                .withIssuer(request.getRequestURI().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(ALGORITHM);
    }

    private String createRefreshToken(User user, HttpServletRequest request) {
        Date expiresAt = new Date((System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION));
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiresAt)
                .withIssuer(request.getRequestURI().toString())
                .sign(ALGORITHM);
    }

}
