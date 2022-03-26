package com.example.spring1.requests;

import lombok.Data;

@Data
public class CommentCreateRequest {
	
	Long id;
	
	Long userId;
	Long postId;
	String text;

}
