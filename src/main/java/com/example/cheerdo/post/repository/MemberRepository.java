package com.example.cheerdo.post.repository;

import com.example.cheerdo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
