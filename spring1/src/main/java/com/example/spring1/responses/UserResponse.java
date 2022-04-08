package com.example.spring1.responses;

import com.example.spring1.entities.User;

import lombok.Data;

@Data
public class UserResponse {
	
	Long id;
	int avatarId;
    String username;
    
    public UserResponse(User user) {
    	this.id=user.getId();
    	this.avatarId=user.getAvatar();
    	this.username=user.getUsername();
    }
}



