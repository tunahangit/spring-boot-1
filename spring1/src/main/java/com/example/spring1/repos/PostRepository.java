package com.example.spring1.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring1.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	
	List<Post> findByUserId(Long userId);

}
