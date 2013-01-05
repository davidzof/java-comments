package com.abcseo.comments.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
	String author;
	String email;
	String comment;
	Date date;
	private static DateFormat df = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm");

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
