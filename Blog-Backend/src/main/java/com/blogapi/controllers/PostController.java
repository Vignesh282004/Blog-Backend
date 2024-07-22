package com.blogapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.Daos.ApiResponse;
import com.blogapi.Daos.PostDao;
import com.blogapi.Daos.PostReponse;
import com.blogapi.configs.AppConstants;
import com.blogapi.services.Impl.PostServiceImpl;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostServiceImpl postServiceImpl;
	
	//create a post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDao> createPost(@RequestBody PostDao postDao,@PathVariable Integer userId,@PathVariable Integer categoryId)
	{
		PostDao postDao2 = this.postServiceImpl.createPost(postDao, userId, categoryId);
		return new ResponseEntity<PostDao>(postDao2,HttpStatus.CREATED);
	}
	
	//update the post 
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDao> updatePost(@RequestBody PostDao postDao ,@PathVariable Integer postId)
	{
		PostDao updatePost = this.postServiceImpl.updatePost(postDao, postId);
		return new ResponseEntity<PostDao>(updatePost,HttpStatus.OK); 
	}
	
	//get a post
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDao> getbywithId(@PathVariable Integer postId)
	{
		PostDao postDao = this.postServiceImpl.getPostById(postId);
		return new ResponseEntity<PostDao>(postDao,HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostReponse> getAllpost(
			 @RequestParam(value = "pageNumber" ,defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			 @RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			 @RequestParam(value = "sortBy", defaultValue =  AppConstants.SORT_BY,required =  false) String sortBy,
			 @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
			) 
	{
		PostReponse postReponse = this.postServiceImpl.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostReponse>(postReponse,HttpStatus.OK);
	}
	
	
	//get all posts of particular user with user id
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDao>> getPostofUser(@PathVariable Integer userId) 
	{
		List<PostDao> postDaos = this.postServiceImpl.getPostsByUser(userId);
		return new ResponseEntity<List<PostDao>>(postDaos,HttpStatus.OK);
	}
	
	
	//get all posts presemt in category 
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDao>> getAllpostinCategory(@PathVariable Integer categoryId)
	{
		List<PostDao> postDaos = this.postServiceImpl.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDao>>(postDaos,HttpStatus.OK);
	}
	
	
	//search post by providing title
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDao>> seachPosts(@PathVariable String keyword) 
	{
		List<PostDao> postDaos = this.postServiceImpl.searchPost(keyword);
		return new ResponseEntity<List<PostDao>>(postDaos,HttpStatus.OK);
	}
	
	
	//delete post 
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId)
	{
		this.postServiceImpl.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully ....", true),HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
