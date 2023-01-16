package com.example.cheerdo.entity;

import com.example.cheerdo.todo.enums.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    private String content;

    @Column(name = "write_date")
    @DateTimeFormat(pattern= "yyyy-MM-dd")
    private LocalDate date;


    @Column(name = "success_flag", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isSuccess;

    @Enumerated(EnumType.STRING)
    private Type type;

    public Todo(Long id, Member member, String content, LocalDate date, boolean isSuccess, Type type) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.date = date;
        this.isSuccess = isSuccess;
        this.type = type;
    }
}
