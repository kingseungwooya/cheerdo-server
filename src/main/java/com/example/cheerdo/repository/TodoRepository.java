package com.example.cheerdo.repository;


import com.example.cheerdo.entity.Calender;
import com.example.cheerdo.entity.Habit;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.entity.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, String> {
    Optional<List<Todo>> findAllByCalenderAndType(Calender calender, Type type);

    void deleteAllByHabit(Habit habit);
}
