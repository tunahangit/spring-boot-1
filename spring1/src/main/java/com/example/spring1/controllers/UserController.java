package com.example.spring1.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring1.entities.User;
import com.example.spring1.services.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping()
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	@PostMapping()
	public User saveUser(@RequestBody User newUser) {
		return userService.saveUser(newUser);
	}
	@GetMapping("{userId}")
	public User getUserById(@PathVariable Long userId) {
		//custom exception
		return userService.getUserById(userId);
	}
	
	@PutMapping("{userId}")
	public User updateUserById(@PathVariable Long userId , @RequestBody User updateUser) {
		return userService.updateUserById(userId, updateUser);
	}
	@DeleteMapping("{userId}")
	public void deleteUserById(@PathVariable Long userId) {
	     userService.deleteUserById(userId);
	}
}
