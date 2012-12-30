package com.abcseo.comments;


public interface Repository {
	public Comments getComments(String uri, int page, int count);
	public void addComment(String uri, Comment c);

}
