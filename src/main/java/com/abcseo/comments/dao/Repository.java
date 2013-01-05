package com.abcseo.comments.dao;

import com.abcseo.comments.dto.Comment;
import com.abcseo.comments.dto.Comments;


public interface Repository {
	public Comments getComments(String uri, int page, int count);
	public void addComment(String uri, Comment c);

}
