package com.example.cheerdo.todo.service;

import com.example.cheerdo.entity.Member;
import com.example.cheerdo.repository.CalenderRepository;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.todo.dto.request.CalenderRequestDto;
import com.example.cheerdo.todo.dto.response.CalenderResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CalenderServiceImpl implements CalenderService {

    private final MemberRepository memberRepository;
    private final CalenderRepository calenderRepository;
    @Override
    public List<CalenderResponseDto> getSuccessRates(CalenderRequestDto calenderRequestDto) {
        Member member = memberRepository.findById(calenderRequestDto.getMemberId()).get();
        String[] yearMonth = calenderRequestDto.getSearchYearMonth().split("-");
        return calenderRepository.findAllByMemberAndYearMonth(member
                , Integer.parseInt(yearMonth[0])
                , Integer.parseInt(yearMonth[1]));
    }
}
