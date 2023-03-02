package com.example.cheerdo.repository;

import com.example.cheerdo.entity.Calender;
import com.example.cheerdo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CalenderRepository extends JpaRepository<Calender, Long> {
    Optional<Calender> findByMemberAndDate(Member member, LocalDate date);

}
