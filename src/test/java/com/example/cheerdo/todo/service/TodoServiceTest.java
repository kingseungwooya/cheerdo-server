package com.example.cheerdo.todo.service;

import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.todo.dto.request.WriteTodoRequestDto;
import com.example.cheerdo.todo.enums.Type;
import com.example.cheerdo.todo.repository.MemberRepository;
import com.example.cheerdo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(true)
class TodoServiceTest {
    private final Logger logger = LoggerFactory.getLogger(TodoServiceTest.class);
    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void init() {

    }

    @Test
    void writeTodo() {
        // given
        Member member = Member.builder()
                .id("kim12345")
                .name("난승우")
                .build();
        memberRepository.save(member);
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
    void getMyTodos() {
        // given

        // when

        // then
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