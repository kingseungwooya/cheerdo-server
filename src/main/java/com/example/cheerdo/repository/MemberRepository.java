package com.example.cheerdo.repository;

import com.example.cheerdo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findById(String id);

    List<Member> findAllByNameContainingIgnoreCaseAndIdContainingIgnoreCase(String str1, String str2);
}
