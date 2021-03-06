package com.example.spring1.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.spring1.entities.Comment;
import com.example.spring1.entities.Post;
import com.example.spring1.entities.User;
import com.example.spring1.repos.CommentRepository;
import com.example.spring1.requests.CommentCreateRequest;
import com.example.spring1.responses.CommentResponse;

@Service
public class CommentService {
	
	private CommentRepository commentRepository;
	private PostService postService;
	private UserService userService;
	
	public CommentService(CommentRepository commentRepository , PostService postService , UserService userService) {
		this.commentRepository=commentRepository;
		this.postService=postService;
		this.userService=userService;
	}
	
	public List<CommentResponse> getAllComments(Optional<Long> userId , Optional<Long> postId) {
		List<Comment> comments;
		    if(userId.isPresent() && postId.isPresent()) {
		    	comments= commentRepository.findByUserIdAndPostId(userId.get(),postId.get());
		    }else if(userId.isPresent()) {
		    	comments= commentRepository.findByUserId(userId.get());
		    }else if(postId.isPresent()) {
		    	comments= commentRepository.findByPostId(postId.get());
		    }else 
		       comments= commentRepository.findAll();
		      return comments.stream().map(c -> new CommentResponse(c)).collect(Collectors.toList());
	}

	public Comment getCommentById(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}

	public Comment createComment(CommentCreateRequest newComment) {
		User user = userService.getUserById(newComment.getUserId());
		Post post  =postService.getPostById(newComment.getPostId());
		if(user ==null || post ==null)
		return null;
		Comment comment = new Comment();
		comment.setId(newComment.getId());
		comment.setText(newComment.getText());
		comment.setPost(post);
		comment.setUser(user);
		comment.setCreateDate(new Date());
		return commentRepository.save(comment);
	}

	public Comment updateComment(Long commentId , CommentCreateRequest commentCreateRequest) {
		Comment comment = commentRepository.findById(commentId).orElse(null);
		if(comment == null)
		return null;
		comment.setText(commentCreateRequest.getText());
		return  commentRepository.save(comment);
		
	}

	public void  deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}
	
	public List<Object> getCommentsForUserService(List<Long> postIds){
		return commentRepository.findUserCommentsByPostId(postIds);
	}
}
