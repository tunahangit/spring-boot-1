package com.example.spring1.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring1.entities.Like;
import com.example.spring1.entities.Post;
import com.example.spring1.entities.User;
import com.example.spring1.repos.LikeRepository;
import com.example.spring1.requests.LikeCreateRequest;
import com.example.spring1.responses.LikeResponse;

@Service
public class LikeService {

	private LikeRepository likeRepository;
	private PostService postService;
	private UserService userService;
	
	public LikeService(LikeRepository likeRepository ,  UserService userService) {
		this.likeRepository=likeRepository;
		this.userService=userService;
	}
	
	@Autowired
	public void setPostService(PostService postService) {
		this.postService=postService;
	}

	public List<LikeResponse> getAllLikes(Optional<Long> postId, Optional<Long> userId) {
		List<Like> list;
		if(postId.isPresent() && userId.isPresent()) {
			list= likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
		}else if(userId.isPresent()) {
			list= likeRepository.findByUserId(userId.get());
		}else if(postId.isPresent()) {
			list= likeRepository.findByPostId(postId.get());
		}else
	    	list= likeRepository.findAll();
		
	   return 	list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
	}

	public Like createLike(LikeCreateRequest likeCreateRequest) {
		 User getUser=userService.getUserById(likeCreateRequest.getUserId());
		 Post getPost=postService.getPostById(likeCreateRequest.getPostId());
		 if(getUser ==null ||  getPost ==null)
		 return null;
		 Like like = new Like();
		 like.setId(likeCreateRequest.getId());
		 like.setPost(getPost);
		 like.setUser(getUser);
		 return likeRepository.save(like);
	}

	public Like getLikeById(Long likeId) {	
		return likeRepository.findById(likeId).orElse(null);
	}

	public void deleteLikeById(Long likeId) {
		likeRepository.deleteById(likeId);
	}
	
	public List<Object> getLikesForUserService(List<Long> postIds){
		return likeRepository.findUserLikesByPostId(postIds);
	}
}
