package com.example.spring1.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring1.entities.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

}
