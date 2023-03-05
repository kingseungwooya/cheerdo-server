package com.example.cheerdo.repository;

import com.example.cheerdo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findById(String id);
    Optional<List<Member>> findAllByIdContainingIgnoreCase(String searchStr1);
    Optional<List<Member>> findAllByNameContainingIgnoreCase(String searchStr1);
}
