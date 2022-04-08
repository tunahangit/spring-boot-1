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

import com.example.spring1.entities.Comment;
import com.example.spring1.requests.CommentCreateRequest;
import com.example.spring1.responses.CommentResponse;
import com.example.spring1.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	private CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService=commentService;
	}
	
   @GetMapping
   public  List<CommentResponse> getAllComments(@RequestParam Optional<Long> userId , @RequestParam Optional<Long> postId){
	   return commentService.getAllComments(userId,postId);
   }
	
	@PostMapping()
	public Comment createComment(@RequestBody CommentCreateRequest commentCreateRequest) {
		return commentService.createComment(commentCreateRequest);
	}
	
	@GetMapping("/{commentId}")
	public Comment getComment(@PathVariable Long commentId) {
		return commentService.getCommentById(commentId);
	}
	
	@PutMapping("/{commentId}") 
	public Comment updateComment(@PathVariable Long commentId , @RequestBody CommentCreateRequest commentCreateRequest) {
		return commentService.updateComment(commentId, commentCreateRequest);
	}
	
	@DeleteMapping("/{commentId}")
	 public void deteleComment(@PathVariable Long commentId) {
		 commentService.deleteComment(commentId);
	}

}
