package com.example.cheerdo.repository;

import com.example.cheerdo.entity.Calender;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.todo.dto.response.CalenderResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.*;

public interface CalenderRepository extends JpaRepository<Calender, Long> {
    Optional<Calender> findByMemberAndDate(Member member, LocalDate date);
    @Query("SELECT new com.example.cheerdo.todo.dto.response.CalenderResponseDto(e.successRate, e.date) FROM Calender e WHERE e.member = :member AND YEAR(e.date) = :year AND MONTH(e.date) = :month")
    List<CalenderResponseDto> findAllByMemberAndYearMonth(@Param("member") Member member,
                                                          @Param("year") int year,
                                                          @Param("month") int month);

}
