package com.example.spring1.responses;

import java.util.List;

import com.example.spring1.entities.Like;
import com.example.spring1.entities.Post;

import lombok.Data;

@Data
public class PostResponse {
	
	Long id;
	Long userId;
	String username;
	String title;
	String text;
	List<LikeResponse> postLikeList;
	
	public PostResponse(Post entity , List<LikeResponse> likeList) {  //parametre alan post nesnesini PostReponse'a dönüştürecek.(Mapper)
		this.id=entity.getId();
		this.userId=entity.getUser().getId();
		this.username=entity.getUser().getUsername();
		this.title=entity.getTitle();
		this.text=entity.getText();
		this.postLikeList=likeList;
	}

}
