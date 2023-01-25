package com.example.cheerdo.repository;

import com.example.cheerdo.entity.Post;
import com.example.cheerdo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
