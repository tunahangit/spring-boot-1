package com.example.spring1.requests;

import lombok.Data;

@Data
public class LikeCreateRequest {
	Long id;
	Long postId;
	Long userId;
}
