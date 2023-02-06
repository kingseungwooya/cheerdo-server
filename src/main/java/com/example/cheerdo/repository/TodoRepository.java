package com.example.cheerdo.repository;


import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.entity.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<List<Todo>> findAllByMemberAndTypeAndDate(@Param("member") Member member, @Param("type")Type type, @Param("date") LocalDate searchDate);

}
