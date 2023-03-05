package com.example.cheerdo.repository;

import com.example.cheerdo.entity.Habit;
import com.example.cheerdo.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    Optional<List<Habit>> findAllByMember(Member member);
}
