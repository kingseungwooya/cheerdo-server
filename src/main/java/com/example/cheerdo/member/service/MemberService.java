package com.example.cheerdo.member.service;

import com.example.cheerdo.member.dto.request.UpdateProfileDto;
import com.example.cheerdo.member.dto.response.MemberInfoResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberService {
    String updateMyInfo(UpdateProfileDto updateProfileDto) throws IOException;

    MemberInfoResponseDto getInfoById(String memberId);

    void getHabitProgress();
}
