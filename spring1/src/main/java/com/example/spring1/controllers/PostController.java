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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring1.entities.Post;
import com.example.spring1.requests.PostCreateRequest;
import com.example.spring1.requests.PostUpdateRequest;
import com.example.spring1.responses.PostResponse;
import com.example.spring1.services.PostService;



@RestController
@RequestMapping("/posts")
public class PostController {
	
	private PostService postService ;
	
	public PostController(PostService postService) {
		this.postService=postService;
	}
	
	@GetMapping()
	 public List<PostResponse>  gettAllPosts(@RequestParam  Optional<Long> userId){ 
		//@RequestParam , request içindeki parametrelerini parse et ve sağında bulunan değişkenin içine at.
		return postService.getAllPosts(userId);
	}
	
	@PostMapping()
	public Post createPost(@RequestBody PostCreateRequest newPost ) {
		return postService.createPost(newPost);
	}
	
	@GetMapping("/{postId}")
	public PostResponse getPostById(@PathVariable Long postId) {
		// @PathVariable , burada ise parametreyi doğrudan alıp sağındaki değişkene atıyor.
		return postService.getPostByIdWithLikes(postId);
	}
	@PutMapping("/{postId}")
	public Post updatePost(@PathVariable Long postId ,@RequestBody PostUpdateRequest postUpdateRequest) {
		return postService.updatePostById(postId , postUpdateRequest);
	}
	@DeleteMapping("/{postId}")
	public void deletePost(@PathVariable Long postId) {
		postService.deletePostById(postId);
	}
	
}
