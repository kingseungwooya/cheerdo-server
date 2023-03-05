package com.example.cheerdo.member.service;

import com.example.cheerdo.member.dto.request.UpdateProfileRequestDto;
import com.example.cheerdo.member.dto.response.FriendInfoResponseDto;
import com.example.cheerdo.member.dto.response.MemberInfoResponseDto;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface MemberService {
    String updateMyInfo(UpdateProfileRequestDto updateProfileRequestDto) throws IOException;

    MemberInfoResponseDto getMyInfo(String memberId);

    FriendInfoResponseDto getFriendInfo(Long relationId);
}
