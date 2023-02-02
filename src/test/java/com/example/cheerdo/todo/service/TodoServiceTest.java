package com.example.cheerdo.todo.service;

import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.TodoRepository;
import com.example.cheerdo.todo.dto.request.GetTodoRequestDto;
import com.example.cheerdo.todo.dto.request.WriteTodoRequestDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import com.example.cheerdo.todo.enums.Type;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Rollback(true)
@RunWith(SpringRunner.class)
class TodoServiceTest {
    private final Logger logger = LoggerFactory.getLogger(TodoServiceTest.class);
    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Todo todo;

    private Member member;

    private static final LocalDate testDate = LocalDate.of(1999, 5, 5);
    @BeforeEach
    void init() {
        // 테스팅 Member
        member = Member.builder()
                .id("kim12345")
                .name("난승우")
                .build();
        memberRepository.save(member);
        // 테스팅 Todo
        todo = Todo.builder()
                .member(member)
                .isSuccess(false)
                .date(testDate)
                .type(Type.TODO)
                .content("테스트용")
                .build();
        todoRepository.save(todo);
    }

    @Test
    @DisplayName("Todo를 작성하고 Todo id를 반환한다. ")
    void writeTodo() {
        // given
        WriteTodoRequestDto writeTodoRequestDto =
                new WriteTodoRequestDto(member.getId(), Type.TODO.name(), "오늘의 할일!~");

        // when
        Long todoId = todoService.writeTodo(writeTodoRequestDto);
        Todo todo = todoRepository.findById(todoId).get();

        // then
        assertEquals(todo.getContent(), writeTodoRequestDto.getTodo());
        assertEquals(todo.getType().toString(), writeTodoRequestDto.getType());
        assertEquals(todo.getMember().getId(), writeTodoRequestDto.getUserId());
    }

    @Test
    @DisplayName("원하는 타입의 Todo가 받아왔는지 확인한다. ")
    void getMyTodos() {
        // given
        GetTodoRequestDto getTodoRequestDto =
                new GetTodoRequestDto(member.getId(), Type.TODO.name(), testDate);

        // when
        List<TodoResponseDto> todoResponseDtos = todoService.getMyTodos(getTodoRequestDto);

        // then
        assertThat(todoResponseDtos.size(), is(1));
        assertThat(todoResponseDtos.stream()
                        .map( item -> item.getTypeOfTodo())
                        .collect(Collectors.toList()),
                containsInAnyOrder(getTodoRequestDto.getType()));

    }
    @Test
    @DisplayName("해당 날짜의 투두를 작성하지 않았을때 ")
    void getEmptyTodos() {
        // given
        LocalDate emptyDate = LocalDate.of(2000, 1, 1);
        GetTodoRequestDto getTodoRequestDto =
                new GetTodoRequestDto(member.getId(), Type.TODO.name(), emptyDate);

        // when
        List<TodoResponseDto> myTodos = todoService.getMyTodos(getTodoRequestDto);
        logger.info(String.valueOf(myTodos.size()));

        // then
        assertThat(myTodos, is(empty()));
    }

    @Test
    void modifyTodo() {
        // given

        // when

        // then
    }

    @Test
    void deleteTodo() {
        // given

        // when

        // then
    }

    @Test
    void success() {
        // given

        // when

        // then
    }
}