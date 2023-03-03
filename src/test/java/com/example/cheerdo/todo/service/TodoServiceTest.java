package com.example.cheerdo.todo.service;

import com.example.cheerdo.entity.Calender;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.repository.CalenderRepository;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.TodoRepository;
import com.example.cheerdo.todo.dto.request.GetTodoRequestDto;
import com.example.cheerdo.todo.dto.request.ModifyTodoRequestDto;
import com.example.cheerdo.todo.dto.request.WriteTodoRequestDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import com.example.cheerdo.entity.enums.Type;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    @Autowired
    private CalenderRepository calenderRepository;

    private Todo todo;

    private Member member;

    private String testMemberId = "kim11111";

    private static final LocalDate testDate = LocalDate.of(1999, 5, 5);

    @BeforeEach
    void init() {
        // 테스팅 Member
        member = Member.builder()
                .id(testMemberId)
                .name("난승우")
                .build();
        memberRepository.save(member);
        // 테스팅 Todo
        todo = Todo.builder()
                .todoId(UUID.randomUUID().toString())
                .isSuccess(false)
                .type(Type.TODO)
                .content("테스트용")
                .build();
        todoRepository.save(todo);
    }

    @Test
    @DisplayName("Todo를 최초 작성시 연관 관계에 있는 calender에 객체가 생성되고 calender내에 todos에 todo가 추가된다.  ")
    void writeTodo() {
        // given
        WriteTodoRequestDto writeTodoRequestDto =
                new WriteTodoRequestDto(UUID.randomUUID().toString()
                        , testMemberId
                        , Type.TODO.name()
                        , LocalDateTime.of(1999, 5, 5, 14, 30)
                        , "테스트"
                        , testDate);

        // when
        String todoId = todoService.writeTodo(writeTodoRequestDto);
        Todo todo = todoRepository.findById(todoId).get();
        Calender calender = todo.getCalender();
        // then
        assertEquals(todo.getContent(), writeTodoRequestDto.getTodo());
        assertEquals(todo.getType().toString(), writeTodoRequestDto.getType());

        // calender가 잘 생성 되었는가
        assertEquals(calender, calenderRepository.findById(calender.getCalenderId()).get());
        // calender의 Todos에 포함되었는가.
        assertThat(calender.getTodos(), contains(todo));
        assertThat(calenderRepository.findById(calender.getCalenderId()).get().getTodos(), contains(todo));


    }


    @Test
    @DisplayName("원하는 타입의 Todo가 받아왔는지 확인한다. ")
    void getMyTodos() {
        // given
        WriteTodoRequestDto writeTodoRequestDto =
                new WriteTodoRequestDto(UUID.randomUUID().toString()
                        , testMemberId
                        , Type.TODO.name()
                        , LocalDateTime.of(1999, 5, 5, 14, 30)
                        , "테스트"
                        , testDate);
        String todoId = todoService.writeTodo(writeTodoRequestDto);

        GetTodoRequestDto getTodoRequestDto =
                new GetTodoRequestDto(testMemberId, Type.TODO.name(), testDate);

        // when
        List<TodoResponseDto> todoResponseDtos = todoService.getMyTodos(getTodoRequestDto);

        // then
        assertThat(todoResponseDtos.size(), is(1));

    }

    @Test
    @DisplayName("해당 날짜의 투두를 작성하지 않았을때 빈 Array를 반환한다.  ")
    void getEmptyTodos() {
        // given
        GetTodoRequestDto emptyGetTodoRequestDto =
                new GetTodoRequestDto(testMemberId, Type.TODO.name(), LocalDate.of(1111, 1, 1));

        // when
        List<TodoResponseDto> emptyResponseDtos = todoService.getMyTodos(emptyGetTodoRequestDto);

        // then
        assertThat(emptyResponseDtos, empty());
    }

    @Test
    @DisplayName("수정이 잘 이루어지는지 확인한다. ")
    void modifyTodo() {
        // given
        WriteTodoRequestDto writeTodoRequestDto =
                new WriteTodoRequestDto(UUID.randomUUID().toString()
                        , testMemberId
                        , Type.TODO.name()
                        , LocalDateTime.of(1999, 5, 5, 14, 30)
                        , "테스트"
                        , testDate);
        String todoId = todoService.writeTodo(writeTodoRequestDto);

        // when
        todoService.modifyTodo(new ModifyTodoRequestDto(todoId, "수정된 할일"));

        // then
        Todo todo = todoRepository.findById(todoId).get();
        assertThat(writeTodoRequestDto.getTodo(), not(todo.getContent()));
        assertThat(todoId, is(todo.getTodoId()));
    }

    @Test
    @DisplayName("Todo를 삭제할 수 있다. ")
    void deleteTodo() {
        // given
        WriteTodoRequestDto writeTodoRequestDto =
                new WriteTodoRequestDto(UUID.randomUUID().toString()
                        , testMemberId
                        , Type.TODO.name()
                        , LocalDateTime.of(1999, 5, 5, 14, 30)
                        , "테스트"
                        , testDate);
        String todoId = todoService.writeTodo(writeTodoRequestDto);

        // when
        todoService.deleteTodo(todoId);

        // then
        Optional<Todo> todo = todoRepository.findById(todoId);
        assertThat(todo.isEmpty(), is(true));
        assertThat(todo.isPresent(), is(false));
    }

    @Test
    @DisplayName("success rate를 update할 수 있다. ")
    void success() {
        // given
        WriteTodoRequestDto writeTodoRequestDto =
                new WriteTodoRequestDto(UUID.randomUUID().toString()
                        , testMemberId
                        , Type.TODO.name()
                        , LocalDateTime.of(1999, 5, 5, 14, 30)
                        , "테스트"
                        , testDate);
        String todoId = todoService.writeTodo(writeTodoRequestDto);

        // when
        todoService.success(todoId);

        // then
        Calender calender = todoRepository.findById(todoId).get().getCalender();
        logger.info("rate is -> {}", calender.getSuccessRate());
        assertThat(calender.getTodos().size(), is(1));
        assertThat(calender.getSuccessRate(), not(0));

    }
}