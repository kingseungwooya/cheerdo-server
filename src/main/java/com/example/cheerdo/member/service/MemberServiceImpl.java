package com.example.cheerdo.member.service;

import com.example.cheerdo.common.enums.SortType;
import com.example.cheerdo.common.sort.SortUtil;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.entity.enums.Type;
import com.example.cheerdo.login.security.filter.CustomAuthorizationFilter;
import com.example.cheerdo.member.dto.request.UpdateProfileRequestDto;
import com.example.cheerdo.member.dto.response.MemberInfoResponseDto;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.TodoRepository;
import com.sun.xml.bind.v2.TODO;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

        Member member = memberRepository.findById(memberId).get();

        Optional<Todo> todo = todoRepository.findFirstByMemberAndType(member
                        , Type.HABIT
                        , SortUtil.sort(SortType.DESC, "date"));

        if (todo.isPresent()) {
           return member.to(calcDayDuration(todo.get().getDate()));
        }
        return member.to(0L);
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


