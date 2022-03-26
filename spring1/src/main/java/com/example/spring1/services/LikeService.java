package com.example.spring1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.spring1.entities.Like;
import com.example.spring1.entities.Post;
import com.example.spring1.entities.User;
import com.example.spring1.repos.LikeRepository;
import com.example.spring1.requests.LikeCreateRequest;

@Service
public class LikeService {

	private LikeRepository likeRepository;
	private PostService postService;
	private UserService userService;
	
	public LikeService(LikeRepository likeRepository , PostService postService , UserService userService) {
		this.likeRepository=likeRepository;
		this.postService=postService;
		this.userService=userService;
	}

	public List<Like> getAllLikes(Optional<Long> postId, Optional<Long> userId) {
		if(postId.isPresent() && userId.isPresent()) {
			return likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
		}else if(userId.isPresent()) {
			return likeRepository.findByUserId(userId.get());
		}else if(postId.isPresent()) {
			return likeRepository.findByPostId(postId.get());
		}else
		return likeRepository.findAll();
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


	
}
