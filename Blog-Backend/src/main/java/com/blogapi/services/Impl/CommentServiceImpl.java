package com.blogapi.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.Daos.CommentDao;
import com.blogapi.entity.Comment;
import com.blogapi.entity.Post;
import com.blogapi.exceptions.ResourceException;
import com.blogapi.repository.CommentRepo;
import com.blogapi.repository.PostRepo;
import com.blogapi.services.CommentService;
@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//create comment to particular post by user ....
	@Override
	public CommentDao createComment(CommentDao commentDao, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceException("post","post id",postId));
		Comment comment = this.modelMapper.map(commentDao,Comment.class);
		comment.setPost(post);
		Comment saveComment = this.commentRepo.save(comment);
		return this.modelMapper.map(saveComment,CommentDao.class);
	}
	
	//delete comment for post
	@Override
	public void deleteComment(int commentId) {
			Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceException("comment","comment id", commentId));
			this.commentRepo.delete(comment);
	}
	
	
}
