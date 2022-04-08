package com.example.spring1.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring1.entities.Post;
import com.example.spring1.entities.User;
import com.example.spring1.repos.PostRepository;
import com.example.spring1.requests.PostCreateRequest;
import com.example.spring1.requests.PostUpdateRequest;
import com.example.spring1.responses.LikeResponse;
import com.example.spring1.responses.PostResponse;

@Service
public class PostService {
	
	 private PostRepository postRepository;
	 private UserService userService;
	 private LikeService likeService;
	 
	 public PostService(PostRepository postRepository , UserService userService) {
		 this.postRepository=postRepository;
		 this.userService=userService;
	 }
	 
	 @Autowired
	 public void setLikeService(LikeService likeService) {  //Üstte yazarsam like post'u çağıracak post da like'ı (döngü oluşacak )
		 this.likeService=likeService; 
	 }

	public List<PostResponse> getAllPosts(Optional<Long> userId) {
		List<Post> list;
		if(userId.isPresent()) {
			list=postRepository.findByUserId(userId.get());
		}
			list= postRepository.findAll();
		return 	list.stream().map(p -> { 
		 List<LikeResponse> likeList=	likeService.getAllLikes(Optional.of(p.getId()),Optional.ofNullable(null));
			return new PostResponse(p , likeList);}).collect(Collectors.toList());	
		
	}

	public Post getPostById(Long postId) {
		return postRepository.findById(postId).orElse(null);
	}
	
	public PostResponse getPostByIdWithLikes(Long postId) {
		Post post = postRepository.findById(postId).orElse(null);
		 List<LikeResponse> likeList=	likeService.getAllLikes(Optional.of(postId),Optional.ofNullable(null));
		 return 	new PostResponse(post,likeList);
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
	 toSave.setCreateDate(new Date());
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
	
	public List<Long>  getPostIdsForUserService(Long userId){
		return postRepository.findTopByUserId(userId);
	
	}
	

}
