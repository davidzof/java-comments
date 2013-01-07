/*
 * Copyright 2013, David George, Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.abcseo.comments.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.abcseo.comments.dto.Comment;
import com.abcseo.comments.dto.CommentResults;

//@Service("Repository")
public class RepositoryMemHashImpl implements Repository {
	HashMap<String, ArrayList<Comment>> repository = new HashMap<String, ArrayList<Comment>>();
	MessageDigest md;

	public RepositoryMemHashImpl() {
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public CommentResults getComments(String uri, int page, int count) {

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

		CommentResults comments = new CommentResults(page, count, allComments.size(), list);

		return comments;
	}

	@Override
	public int addComment(String uri, Comment c) {
		ArrayList<Comment> comments = repository.get(uri);
		if (comments == null) {
			comments = new ArrayList<Comment>();
			repository.put(uri, comments);
		}
		comments.add(c);
		
		return comments.size() -1;
	}

}
