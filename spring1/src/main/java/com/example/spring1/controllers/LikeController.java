package com.example.spring1.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring1.entities.Like;
import com.example.spring1.requests.LikeCreateRequest;
import com.example.spring1.services.LikeService;

@RestController
@RequestMapping("/likes")
public class LikeController {
	
	private LikeService likeService;
	
	public LikeController(LikeService likeService) {
		this.likeService=likeService;
	}
	
	@GetMapping
	public List<Like> getAllLikes(@RequestParam Optional<Long> postId , @RequestParam Optional<Long> userId){
		return likeService.getAllLikes(postId , userId);
	}
	@PostMapping
	public Like createLike(@RequestBody LikeCreateRequest likeCreateRequest ) {
		return likeService.createLike(likeCreateRequest);
	}
	
	@GetMapping("/{likeId}")
	public Like getLikeById(@PathVariable Long likeId) {
		return likeService.getLikeById(likeId);
	}

	@DeleteMapping("/{likeId}")
	public void deleteLikeById(@PathVariable Long likeId) {
		 likeService.deleteLikeById(likeId);
	}
	
}
