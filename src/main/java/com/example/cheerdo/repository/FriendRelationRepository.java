package com.example.cheerdo.repository;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {
}
