package com.example.spring1.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring1.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
