package com.example.cheerdo.entity;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Calender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calender_id")
    private Long calenderId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "success_rate")
    private double successRate;

    @OneToMany(mappedBy = "calender")
    private List<Todo> todos = new ArrayList<>();

    @Builder
    public Calender(Long calenderId, Member member, LocalDate date) {
        this.calenderId = calenderId;
        this.member = member;
        this.date = date;
        this.successRate = 0;
    }

    public void updateRate() {
        long totalSize = todos.size();
        long successCount = todos.stream()
                .filter(todo -> todo.isSuccess())
                .count();
        successRate = ((double) successCount / (double) totalSize) * 100;

    }

    public void addTodo(Todo todo) {
        todos.add(todo);
    }


    public void deleteTodo(Todo todo) {
        todos.remove(todo);
    }
}
