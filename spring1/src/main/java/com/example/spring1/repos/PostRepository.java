package com.example.spring1.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring1.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
