package com.example.cheerdo.todo.service;

import com.example.cheerdo.entity.Calender;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.repository.CalenderRepository;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.todo.dto.request.GetTodoRequestDto;
import com.example.cheerdo.todo.dto.request.ModifyTodoRequestDto;
import com.example.cheerdo.todo.dto.request.WriteTodoRequestDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import com.example.cheerdo.entity.enums.Type;
import com.example.cheerdo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;
    private final CalenderRepository calenderRepository;
    // todo 작성 요청시 해당 date에 객체가 있는지 확인? 확인 후 없으면 생성
    @Override
    public String writeTodo(WriteTodoRequestDto writeTodoRequestDto) {
        Member member = memberRepository.findById(writeTodoRequestDto.getMemberId()).get();
        Calender calender =
                calenderRepository.findByMemberAndDate(member, writeTodoRequestDto.getDate())
                        .orElse(calenderRepository.save(
                                Calender.builder()
                                        .member(member)
                                        .date(writeTodoRequestDto.getDate())
                                        .build()
                        ));

        Todo todo = todoRepository.save(writeTodoRequestDto.requestToEntity(calender));
        calender.addTodo(todo);
        return todo.getTodoId();
    }

    @Override
    public List<TodoResponseDto> getMyTodos(GetTodoRequestDto getTodoRequestDto) {
        Member member = memberRepository.findById(getTodoRequestDto.getMemberId()).get();
        Optional<Calender> calender = calenderRepository.findByMemberAndDate(member, getTodoRequestDto.getSearchDate());

        if (calender.isEmpty()) {
            return new ArrayList<>();
        }
        List<Todo> todos = todoRepository.findAllByCalenderAndType(calender.get()
                , Type.valueOf(getTodoRequestDto.getType())).get();

        return todos.stream()
                .map(todo -> todo.entityToTodoResponseDto())
                    .collect(Collectors.toList());
    }

    @Override
    public void modifyTodo(ModifyTodoRequestDto modifyTodoRequestDto) {
        Todo todo = todoRepository.findById(modifyTodoRequestDto.getTodoId())
                .orElseThrow(() -> new IllegalArgumentException("maybe todo is deleted"));
        todo.updateContent(modifyTodoRequestDto.getTodo());
    }

    @Override
    public void deleteTodo(String todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("maybe todo is deleted"));
        todoRepository.delete(todo);
    }

    @Override
    public void success(String todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("maybe todo is deleted"));
        todo.success();
        Long calenderId = todo.getCalender().getCalenderId();
        Calender calender = calenderRepository.findById(calenderId).get();
        calender.updateRate();
    }
}
