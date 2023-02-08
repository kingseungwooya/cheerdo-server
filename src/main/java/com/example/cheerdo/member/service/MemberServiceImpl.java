package com.example.cheerdo.member.service;

import com.example.cheerdo.entity.Member;
import com.example.cheerdo.member.dto.request.UpdateProfileRequestDto;
import com.example.cheerdo.member.dto.response.MemberInfoResponseDto;
import com.example.cheerdo.repository.MemberRepository;

import java.io.IOException;

public class MemberServiceImpl implements MemberService {

    MemberRepository memberRepository;

    @Override
    public String updateMyInfo(UpdateProfileRequestDto updateProfileRequestDto) throws IOException {
        Member member = memberRepository.findById(updateProfileRequestDto.getMemberId()).get();
        member.updateMemberImage(updateProfileRequestDto.getUploadImage().getBytes());
        member.updateBio(updateProfileRequestDto.getUpdateBio());
        member.updateName(updateProfileRequestDto.getUpdateName());
        memberRepository.save(member);
        final String successMessage = "profile is successly changed";
        return successMessage;
    }

    @Override
    public MemberInfoResponseDto getInfoById(String memberId) {
        return memberRepository.findById(memberId).get().to();
    }

    @Override
    public void getHabitProgress() {

    }
}


