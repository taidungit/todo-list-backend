package com.example.todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todo.domain.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Long> {
   Optional<Todo>findByUsername(String username);
    void deleteByUsername(String username);
}
