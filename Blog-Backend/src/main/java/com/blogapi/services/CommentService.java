package com.blogapi.services;

import com.blogapi.Daos.CommentDao;

public interface CommentService {

	CommentDao createComment(CommentDao commentDao,Integer postId);
	
	void deleteComment(int commentId);
}
