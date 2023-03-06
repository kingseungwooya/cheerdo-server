package com.example.cheerdo.entity;

import com.example.cheerdo.todo.dto.response.HabitResponseDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import com.example.cheerdo.entity.enums.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Todo {
    @Id
    @Column(name = "todo_id")
    private String todoId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "calender_id")
    private Calender calender;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "habit_id")
    private Habit habit;

    @Lob
    private String content;

    @Column(name = "end_date_time")
    private String endDateTime;

    @Column(name = "success_flag", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isSuccess;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Builder
    public Todo(String todoId, Calender calender, String content, boolean isSuccess, Type type, String endDateTime, Habit habit) {
        this.todoId = todoId;
        this.calender = calender;
        this.content = content;
        this.isSuccess = isSuccess;
        this.type = type;
        this.endDateTime = endDateTime;
        this.habit = habit;
    }

    public TodoResponseDto entityToTodoResponseDto() {
        return TodoResponseDto.builder()
                .todoId(todoId)
                .typeOfTodo(type.name())
                .todo(content)
                .success(isSuccess)
                .endDateTime(endDateTime)
                .build();
    }

    public HabitResponseDto entityToHabitResponseDto() {
        return HabitResponseDto.builder()
                .habitId(habit.getId())
                .todoId(todoId)
                .typeOfTodo(type.name())
                .content(content)
                .success(isSuccess)
                .build();
    }

    public void updateContent(String updatedContent) {
        this.content = updatedContent;
    }

    public void success() {
        // 성공을 취소로
        if (isSuccess) {
            this.isSuccess = false;
            return;
        }
        // 취소를 성공으로
        this.isSuccess = true;

    }

}
