package com.example.cheerdo.todo.service;

import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.todo.dto.request.GetTodoRequestDto;
import com.example.cheerdo.todo.dto.request.ModifyTodoRequestDto;
import com.example.cheerdo.todo.dto.request.WriteTodoRequestDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import com.example.cheerdo.todo.enums.Type;
import com.example.cheerdo.todo.repository.MemberRepository;
import com.example.cheerdo.todo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long writeTodo(WriteTodoRequestDto writeTodoRequestDto) {
        Member member = memberRepository.findById(writeTodoRequestDto.getUserId()).get();
        Todo todo = todoRepository.save(writeTodoRequestDto.requestToEntity(member));
        return todo.getId();
    }

    @Override
    public List<TodoResponseDto> getMyTodos(GetTodoRequestDto getTodoRequestDto) {
        Member member = memberRepository.findById(getTodoRequestDto.getUserId()).get();

        List<Todo> todos = todoRepository.findAllByMemberAndTypeAndDate(member
                        , Type.valueOf(getTodoRequestDto.getType())
                        , getTodoRequestDto.getSearchDate())
                .orElseThrow(() -> new IllegalArgumentException(
                        MessageFormat.format("you didn't write nothing about {0}"
                                , getTodoRequestDto.getType())));

        return todos.stream()
                .map(todo -> todo.entityToTodoResponseDto())
                .collect(Collectors.toList());
    }

    @Override
    public void modifyTodo(ModifyTodoRequestDto modifyTodoRequestDto) {

    }

    @Override
    public void deleteTodo(Long todoId) {

    }

    @Override
    public void updateTodo(Long todoId) {

    }
}
