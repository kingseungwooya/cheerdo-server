package com.example.cheerdo.member.service;

import com.example.cheerdo.member.dto.request.UpdateProfileRequestDto;
import com.example.cheerdo.member.dto.response.MemberInfoResponseDto;

import java.io.IOException;

public interface MemberService {
    String updateMyInfo(UpdateProfileRequestDto updateProfileRequestDto) throws IOException;

    MemberInfoResponseDto getInfoById(String memberId);

    void getHabitProgress();
}
