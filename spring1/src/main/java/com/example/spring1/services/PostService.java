package com.example.spring1.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.spring1.entities.Post;
import com.example.spring1.entities.User;
import com.example.spring1.repos.PostRepository;
import com.example.spring1.requests.PostCreateRequest;
import com.example.spring1.requests.PostUpdateRequest;
import com.example.spring1.responses.PostResponse;

@Service
public class PostService {
	
	 private PostRepository postRepository;
	 private UserService userService;
	 
	 public PostService(PostRepository postRepository , UserService userService) {
		 this.postRepository=postRepository;
		 this.userService=userService;
	 }

	public List<PostResponse> getAllPosts(Optional<Long> userId) {
		List<Post> list;
		if(userId.isPresent()) {
			list=postRepository.findByUserId(userId.get());
		}
			list= postRepository.findAll();
		return 	list.stream().map(p -> new PostResponse(p)).collect(Collectors.toList());
		
	}

	public Post getPostById(Long id) {
		return postRepository.findById(id).orElse(null);
	}

	public Post createPost(PostCreateRequest newPost) {
	 User user=	userService.getUserById(newPost.getUserId());
	 if(user == null)
		 return null;
	 Post toSave = new Post();
	 toSave.setId(newPost.getId());
	 toSave.setTitle(newPost.getTitle());
	 toSave.setText(newPost.getText());
	 toSave.setUser(user);
	  return postRepository.save(toSave);
	}

	public Post updatePostById(Long postId, PostUpdateRequest postUpdateRequest) {
		Post updatePost=postRepository.findById(postId).orElse(null);
		if(updatePost == null)
			return null;
		updatePost.setTitle(postUpdateRequest.getTitle());
		updatePost.setText(postUpdateRequest.getText());
		return postRepository.save(updatePost);
	}

	public void deletePostById(Long postId) {
		postRepository.deleteById(postId);
	}
	

}
