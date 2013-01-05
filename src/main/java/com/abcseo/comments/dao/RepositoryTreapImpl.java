package com.abcseo.comments.dao;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.abcseo.comments.dto.Comment;
import com.abcseo.comments.dto.Comments;

import fx.sunjoy.algo.impl.DiskTreap;
import fx.sunjoy.utils.FastString;

@Service("Repository")
public class RepositoryTreapImpl implements Repository {
	DiskTreap<FastString, byte[]> treap;
	MessageDigest md;

	public RepositoryTreapImpl() {
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		treap = new DiskTreap<FastString, byte[]>(new File(
				"c:/usr/treapdb/hashes"));
	}
	
	@Override
	public Comments getComments(String uri, int page, int count) {
		byte[] bytesOfMessage;
		try {
			bytesOfMessage = uri.getBytes("UTF-8");

			byte[] digest = md.digest(bytesOfMessage);
			System.out.println("digest length " + digest.length);
			for (int i = 0; i < digest.length ; i++) {
				System.out.print(digest[i] + " ");
			}
			String str = new String(digest, "UTF-8");
			System.out.println("string as md5 " + str);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
