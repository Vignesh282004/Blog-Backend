package com.blogapi.services;

import java.util.List;

import com.blogapi.Daos.PostDao;
import com.blogapi.Daos.PostReponse;

public interface PostService {
	
PostDao createPost(PostDao postDao,Integer userId,Integer cateogryId);
	
//	update post
	PostDao updatePost(PostDao postDao,Integer postId);
	
//	get all post
	PostReponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy, String sortDir);

// get single post
	PostDao getPostById(Integer postId); 
	
//	get all posts by user
	List<PostDao> getPostsByUser(Integer userId);
	
//	get all posts by category
	List<PostDao> getPostsByCategory(Integer categoryId);
	
//	delete post
	void deletePost(Integer postId);
	
//	search post
	List<PostDao> searchPost(String title);
}
