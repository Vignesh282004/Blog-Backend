package com.blogapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.Daos.ApiResponse;
import com.blogapi.Daos.CommentDao;
import com.blogapi.services.Impl.CommentServiceImpl;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentServiceImpl commentServiceImpl;
	
	@PostMapping("/post/{postId}/comment")
	public ResponseEntity<CommentDao> createComment(@RequestBody CommentDao commentDao,@PathVariable Integer postId)
	{
		CommentDao commentDao2 = this.commentServiceImpl.createComment(commentDao, postId);
		return new ResponseEntity<CommentDao>(commentDao2,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
	{
		this.commentServiceImpl.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted Successfully", true),HttpStatus.OK);
	}
}
