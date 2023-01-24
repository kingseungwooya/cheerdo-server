package com.example.cheerdo.post.service;

import com.example.cheerdo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
