package com.example.cheerdo.member.service;

import com.example.cheerdo.common.enums.SortType;
import com.example.cheerdo.common.sort.SortUtil;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.enums.Type;
import com.example.cheerdo.member.dto.request.UpdateProfileRequestDto;
import com.example.cheerdo.member.dto.response.MemberInfoResponseDto;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.TodoRepository;
import lombok.AllArgsConstructor;
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

        LocalDate localDate = todoRepository.findFirstByMemberAndType(member
                        , Type.HABIT
                        , SortUtil.sort(SortType.DESC, "date"))
                .get().getDate();

        return member.to(calcDayDuration(localDate));
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


