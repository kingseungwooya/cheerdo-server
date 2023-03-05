package com.example.cheerdo.member.service;


import com.example.cheerdo.entity.Habit;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.member.dto.request.UpdateProfileRequestDto;
import com.example.cheerdo.member.dto.response.MemberInfoResponseDto;
import com.example.cheerdo.repository.HabitRepository;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.TodoRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberRepository memberRepository;
    private final HabitRepository habitRepository;

    @Override
    public String updateMyInfo(UpdateProfileRequestDto updateProfileRequestDto) {
        Member member = memberRepository.findById(updateProfileRequestDto.getMemberId()).get();
        member.updateMemberImage(updateProfileRequestDto.getUploadImage());
        member.updateBio(updateProfileRequestDto.getUpdateBio());
        member.updateName(updateProfileRequestDto.getUpdateName());
        memberRepository.save(member);
        final String successMessage = "profile is successly changed";
        return successMessage;
    }

    @Override
    public MemberInfoResponseDto getInfoById(String memberId) {

        Member member = memberRepository.findById(memberId).get();
        Optional<Habit> habit = habitRepository.findFirstByMemberOrderByDDayDesc(member);
        if (habit.isPresent()) {
           return member.to(habit.get().getDDay());
        }
        return member.to(0);
    }

    @Override
    public void getHabitProgress() {

    }
}


