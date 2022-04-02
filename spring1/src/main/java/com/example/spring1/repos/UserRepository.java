package com.example.spring1.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring1.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	

	User findByUsername(String username);

	

	

}
