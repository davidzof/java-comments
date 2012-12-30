package com.abcseo.comments;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

@Service("Repository")
public class RepositoryImpl implements Repository {
	HashMap<String, ArrayList<Comment>> repository = new HashMap<String, ArrayList<Comment>>();

	@Override
	public Comments getComments(String uri, int page, int count) {
		ArrayList<Comment> allComments = repository.get(uri);
		if (allComments == null) {
			return null;
		}
		ArrayList<Comment> list = new ArrayList<Comment>(count);
		int start = page * count;
		for (int i = start; i < start + count && i < allComments.size(); i++) {
			Comment c = allComments.get(i);
			list.add(c);
		}
		
		Comments comments = new Comments(page, count, allComments.size(), list);

		return comments;
	}

	@Override
	public void addComment(String uri, Comment c) {
		ArrayList<Comment> comments = repository.get(uri);
		if (comments == null) {
			comments = new ArrayList<Comment>();
			repository.put(uri, comments);
		}
		comments.add(c);
	}

}
