package com.example.cheerdo.todo.service;

import com.example.cheerdo.todo.dto.request.GetTodoRequestDto;
import com.example.cheerdo.todo.dto.request.ModifyTodoRequestDto;
import com.example.cheerdo.todo.dto.request.WriteTodoRequestDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;

import java.util.List;

public interface TodoService {
    String writeTodo(WriteTodoRequestDto writeTodoRequestDto);

    List<TodoResponseDto> getMyTodos(GetTodoRequestDto getTodoRequestDto);

    void modifyTodo(ModifyTodoRequestDto modifyTodoRequestDto);

    void deleteTodo(String todoId);

    void success(String todoId);

}
