package com.example.spring1.responses;

import com.example.spring1.entities.Like;

import lombok.Data;

@Data
public class LikeResponse {
	
	Long id;
	Long postId;
	Long userId;
	
	
	public LikeResponse(Like entity) {
		this.id=entity.getId();
		this.postId=entity.getPost().getId();
		this.userId=entity.getUser().getId();
	}

}
