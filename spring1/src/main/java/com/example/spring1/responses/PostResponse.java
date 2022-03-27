package com.example.spring1.responses;

import com.example.spring1.entities.Post;

import lombok.Data;

@Data
public class PostResponse {
	
	Long id;
	Long userId;
	String userName;
	String title;
	String text;
	
	public PostResponse(Post entity) {  //parametre alan post nesnesini PostReponse'a dönüştürecek.(Mapper)
		this.id=entity.getId();
		this.userId=entity.getUser().getId();
		this.userName=entity.getUser().getUsername();
		this.title=entity.getTitle();
		this.text=entity.getText();
	}

}
