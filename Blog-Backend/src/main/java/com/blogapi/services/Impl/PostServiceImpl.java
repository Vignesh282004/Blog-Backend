package com.blogapi.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.bytecode.internal.bytebuddy.PrivateAccessorException;
import org.hibernate.metamodel.model.domain.internal.AbstractSqmPathSource;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapi.Daos.PostDao;
import com.blogapi.Daos.PostReponse;
import com.blogapi.entity.Category;
import com.blogapi.entity.Post;
import com.blogapi.entity.User;
import com.blogapi.exceptions.ResourceException;
import com.blogapi.repository.CategoryRepo;
import com.blogapi.repository.PostRepo;
import com.blogapi.repository.UserRepo;
import com.blogapi.services.PostService;

import ch.qos.logback.core.status.NopStatusListener;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public PostDao createPost(PostDao postDao, Integer userId, Integer cateogryId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceException("User","User id",userId));
		Category category = categoryRepo.findById(cateogryId).orElseThrow(() -> new ResourceException("category ","category id",cateogryId));
		Post post = this.modelMapper.map(postDao,Post.class);
		post.setCategory(category);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		
		Post createPost = postRepo.save(post);
		return this.modelMapper.map(createPost, PostDao.class);
	}

	@Override
	public PostDao updatePost(PostDao postDao, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceException("Post","Post id",postId));
		
		post.setContent(postDao.getContent());
		post.setTitle(postDao.getTitle());
		post.setImageName(postDao.getImageName());
		
		Post updatePost = postRepo.save(post);
		return this.modelMapper.map(updatePost, PostDao.class);
	}

	@Override
	public PostReponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort= null;
		
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
		
		Page<Post> page = this.postRepo.findAll(pageable);
		
		List<Post> posts = page.getContent();
		List<PostDao> postDaos = posts.stream().map((post) -> this.modelMapper.map(post, PostDao.class)).collect(Collectors.toList());
		
		PostReponse postReponse = new PostReponse();
		postReponse.setContent(postDaos);
		postReponse.setPageNumber(page.getNumber());
		postReponse.setPageSize(page.getSize());
		postReponse.setTotalElements(page.getTotalElements());
		postReponse.setTotalPages(page.getTotalPages());
		postReponse.setLastPage(page.isLast());
		
		return postReponse;
		}

	@Override
	public PostDao getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceException("Post","Post id",postId));
		return this.modelMapper.map(post,PostDao.class);
	}

	@Override
	public List<PostDao> getPostsByUser(Integer userId) {
			User user = userRepo.findById(userId).orElseThrow(() -> new ResourceException("user","user id",userId));
			List<Post> posts = this.postRepo.findByUser(user);
			List<PostDao> postDaos = posts.stream().map((post) -> this.modelMapper.map(post, PostDao.class)).collect(Collectors.toList());
			return postDaos;
	}

	@Override
	public List<PostDao> getPostsByCategory(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceException("category","category id",categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		
		List<PostDao> postDaos = posts.stream().map((post) -> this.modelMapper.map(post,PostDao.class)).collect(Collectors.toList());
		return postDaos;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceException("post","post id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public List<PostDao> searchPost(String title) {
		List<Post> posts = this.postRepo.findByTitleContaining(title);
		List<PostDao> postDaos = posts.stream().map((post) -> this.modelMapper.map(post, PostDao.class)).collect(Collectors.toList());
		return postDaos;
	}
	
	
	
}
