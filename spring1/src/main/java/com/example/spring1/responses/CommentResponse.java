package com.example.spring1.responses;

import com.example.spring1.entities.Comment;

import lombok.Data;

@Data
public class CommentResponse {
	
	Long id;
	Long userId;
	String text;
	String username;

	public  CommentResponse(Comment comment) {
		this.id=comment.getId();
		this.userId=comment.getUser().getId();
		this.text=comment.getText();
		this.username=comment.getUser().getUsername();
	}
}
