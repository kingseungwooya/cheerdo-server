package com.example.cheerdo.login.service;

import com.example.cheerdo.login.dto.request.JoinRequestDto;
import com.example.cheerdo.login.dto.response.JoinResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    JoinResponseDto join(JoinRequestDto joinRequestDto);
}
