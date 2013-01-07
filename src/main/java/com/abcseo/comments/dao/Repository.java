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

import com.abcseo.comments.dto.Comment;
import com.abcseo.comments.dto.CommentResults;

/**
 * Comment Repository Interface. A user can add a comment or get a list of comments.
 * 
 * @author David George
 *
 */
public interface Repository {
	/**
	 * @param uri	URI of page for which we want a comment listing
	 * @param page
	 * @param count
	 * 
	 * @return CommentResult object
	 */
	public CommentResults getComments(String uri, int page, int count);
	
	/**
	 * @param uri	URI of page to which comment is added. There is no max URI length
	 * @param c		Comment to add
	 * @returns		Comment ID
	 */
	public int addComment(String uri, Comment c);
}
