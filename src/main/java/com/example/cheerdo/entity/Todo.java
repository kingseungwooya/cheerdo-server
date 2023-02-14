package com.example.cheerdo.entity;

import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import com.example.cheerdo.todo.enums.Type;
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
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    private String content;

    @Column(name = "write_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Column(name = "success_flag", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isSuccess;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Builder
    public Todo(String todoId, Member member, String content, LocalDate date, boolean isSuccess, Type type, LocalDateTime endDateTime) {
        this.todoId = todoId;
        this.member = member;
        this.content = content;
        this.date = date;
        this.isSuccess = isSuccess;
        this.type = type;
        this.endDateTime = endDateTime;
    }

    public TodoResponseDto entityToTodoResponseDto() {
        return TodoResponseDto.builder()
                .todoId(todoId)
                .typeOfTodo(type.name())
                .todo(content)
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
