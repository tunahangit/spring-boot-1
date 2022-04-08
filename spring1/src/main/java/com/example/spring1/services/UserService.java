package com.example.spring1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.spring1.entities.Comment;
import com.example.spring1.entities.Like;
import com.example.spring1.entities.Post;
import com.example.spring1.entities.User;
import com.example.spring1.repos.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private LikeService likeService;
	private CommentService commentService;
	private PostService postService;
	
	public UserService(UserRepository userRepository   ) {
		this.userRepository= userRepository;
	}
	
	@Autowired
	public void setPostService(PostService postService) {
		this.postService=postService;
	}
	@Autowired
	public void setLikeService(LikeService likeService) {
		this.likeService=likeService;
	}
	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService=commentService;
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public User saveUser( User newUser) {
		return userRepository.save(newUser);
	}
	
	public User getUserById( Long userId) {
		//custom exception
		return userRepository.findById(userId).orElse(null);
	}
	
	public User updateUserById( Long userId ,  User updateUser) {
		Optional<User> user= userRepository.findById(userId);
		if(user.isPresent()) {
		    User foundUser=user.get();
		    foundUser.setUsername(updateUser.getUsername());
		    foundUser.setPassword(updateUser.getPassword());
		    foundUser.setAvatar(updateUser.getAvatar());
		    userRepository.save(foundUser);
		    return foundUser;
		}else
			return null;
	}
	
	public void deleteUserById(Long userId) {
	     userRepository.deleteById(userId);
	}

	public User getUserByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	public List<Object> getUserActivity(Long userId) {
		List<Long> postIds= postService.getPostIdsForUserService(userId);
		if(postIds.isEmpty())
			return null;
		List<Object> comments = commentService.getCommentsForUserService(postIds);
		List<Object> likes = likeService.getLikesForUserService(postIds);
		List<Object> result = new ArrayList<>();
		result.addAll(comments);
		result.addAll(likes);
		return result;
	}

}
