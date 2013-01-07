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
package com.abcseo.comments.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Comment Object
 * 
 * @author David George
 */
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;
	private String author;
	private String email;
	private String comment;
	private int id;
	private Date date;
	private static DateFormat df = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm");

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Comment() {
		date = new Date();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return df.format(date);
	}

	@Override
	public String toString() {
		return "Comment [author=" + author + ", email=" + email + ", comment="
				+ comment + "]";
	}
}
