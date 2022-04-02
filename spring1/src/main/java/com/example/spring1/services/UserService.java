package com.example.spring1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.spring1.entities.User;
import com.example.spring1.repos.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository= userRepository;
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public User saveUser( User newUser) {
		return userRepository.save(newUser);
	}
	
	public User getUserById( Long userId) {
		//custom exception
		return userRepository.findById(userId).orElse(null);
	}
	
	public User updateUserById( Long userId ,  User updateUser) {
		Optional<User> user= userRepository.findById(userId);
		if(user.isPresent()) {
		    User foundUser=user.get();
		    foundUser.setUsername(updateUser.getUsername());
		    foundUser.setPassword(updateUser.getPassword());
		    userRepository.save(foundUser);
		    return foundUser;
		}else
			return null;
	}
	
	public void deleteUserById(Long userId) {
	     userRepository.deleteById(userId);
	}

	public User getUserByUserName(String username) {
		return userRepository.findByUsername(username);
	}

}
