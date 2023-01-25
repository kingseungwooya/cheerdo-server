package com.example.cheerdo.repository;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
