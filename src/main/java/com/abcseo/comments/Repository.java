package com.abcseo.comments;

import java.util.ArrayList;

public interface Repository {
	public Comments getComments(String uri, int page, int count);
	public void addComment(String uri, Comment c);

}
