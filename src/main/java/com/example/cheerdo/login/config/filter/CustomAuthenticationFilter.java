package com.example.cheerdo.login.config.filter;

import com.example.cheerdo.login.config.CustomUser;
import com.example.cheerdo.login.config.util.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * 회원 로그인 검증을 위한 filter
 */

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private TokenProvider jwtUtil;
    private final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFilter.class);
    // 인증을 수행하는 객체


    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, TokenProvider jwtUtil ) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
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
        CustomUser user = (CustomUser) authentication.getPrincipal();

        String access_token = jwtUtil.generateAccessToken(user);
        String refresh_token = jwtUtil.generateRefreshToken(user);

        // 클라이언트에 토큰 보내기
        /* 헤더에다가만 보내기
        response.setHeader("access_token",access_token);
        response.setHeader("refresh_token",refresh_token);*/
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        tokens.put("memberId", user.getUsername());
        tokens.put("coinCount", String.valueOf(user.getCoinCount()));
        tokens.put("newLetterCount", String.valueOf(user.getNewLetterCount()));
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        Map<String, String> errors = new HashMap<>();
        errors.put("error_message", "unsuccessfulAuthentication - by kimseungwoo");
        new ObjectMapper().writeValue(response.getOutputStream(), errors);
    }
}
