package com.example.cheerdo.member.service;


import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.member.dto.request.UpdateProfileRequestDto;
import com.example.cheerdo.member.dto.response.MemberInfoResponseDto;
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
    private final TodoRepository todoRepository;

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
        /*
        Member member = memberRepository.findById(memberId).get();
        // 추후 수정
        Optional<Todo> todo = null;

        if (todo.isPresent()) {
           return member.to(calcDayDuration(todo.get().getCalender().getDate()));
        }
        return member.to(0L);*/
        return null;
    }

    private Long calcDayDuration(LocalDate localDate) {
        LocalDate now = LocalDate.parse(LocalDate
                .now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return ChronoUnit.DAYS.between(now, localDate);
    }

    @Override
    public void getHabitProgress() {

    }
}


