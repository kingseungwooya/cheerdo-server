package com.example.cheerdo.todo.service;

import com.example.cheerdo.entity.Calender;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.entity.enums.Type;
import com.example.cheerdo.repository.CalenderRepository;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.TodoRepository;
import com.example.cheerdo.todo.dto.request.CalenderRequestDto;
import com.example.cheerdo.todo.dto.request.WriteTodoRequestDto;
import com.example.cheerdo.todo.dto.response.CalenderResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
@Transactional
@Rollback(true)
@RunWith(SpringRunner.class)
class CalenderServiceTest {
    @Autowired
    private CalenderService calenderService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    private String testMemberId = "kim11111";

    private LocalDate testDate = LocalDate.of(1999, 5, 5);

    @BeforeEach
    void init() {
        // 테스팅 Member
        member = Member.builder()
                .id(testMemberId)
                .name("난승우")
                .build();
        memberRepository.save(member);

    }
    @Test
    @DisplayName("Calender의 rate를 잘 갖고오는지 확인한다. ")
    void getSuccessRate() {
        // given
        WriteTodoRequestDto writeTodoRequestDto =
                new WriteTodoRequestDto(UUID.randomUUID().toString()
                        , testMemberId
                        , Type.TODO.name()
                        , "15:50"
                        , "테스트"
                        , testDate);
        WriteTodoRequestDto writeTodoRequestDto2 =
                new WriteTodoRequestDto(UUID.randomUUID().toString()
                        , testMemberId
                        , Type.TODO.name()
                        , "15:50"
                        , "테스트"
                        , testDate);
        String todoId = todoService.writeTodo(writeTodoRequestDto);
        String todoId2 = todoService.writeTodo(writeTodoRequestDto2);

        todoService.success(todoId);

        // when
        // YearMonth yearMonth = YearMonth.of(testDate.getYear(), testDate.getMonth());
        String testYearMonth = "1999-5";
        CalenderRequestDto calenderRequestDto = new CalenderRequestDto(testMemberId, testYearMonth);
        List<CalenderResponseDto> responses = calenderService.getSuccessRates(calenderRequestDto);

        // then
        assertThat(responses.size(), is(1));
        assertThat(responses.get(0).getDate(), is(testDate));
        assertThat(responses.get(0).getSuccessRate(), is(50.0));

    }

}