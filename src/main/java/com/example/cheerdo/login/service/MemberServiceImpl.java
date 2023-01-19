package com.example.cheerdo.login.service;

import com.example.cheerdo.login.dto.request.JoinRequestDto;
import com.example.cheerdo.login.dto.response.JoinResponseDto;
import com.example.cheerdo.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public JoinResponseDto join(JoinRequestDto joinRequestDto) {
        return null;
    }
}
