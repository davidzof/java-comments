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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abcseo.comments.dto.Comment;
import com.abcseo.comments.dto.CommentResults;

import fx.sunjoy.algo.impl.DiskTreap;
import fx.sunjoy.utils.FastString;

/**
 * Implementation of a comment repository backed by a treap database. A treap is
 * a binary tree. It gives reasonable performance up to 1 million entries on an
 * AMD x2
 */
@Service("TreapRepository")
public class RepositoryTreapImpl implements Repository {
	@Value("${repo.dir}")
	private String repoDir;

	DiskTreap<FastString, byte[]> treap;
	MessageDigest md;

	public RepositoryTreapImpl() throws NoSuchAlgorithmException {
		md = MessageDigest.getInstance("SHA-1");
	}

	@PostConstruct
	public void initialize() throws Exception {
		File repository = new File(repoDir);
		treap = new DiskTreap<FastString, byte[]>(repository);
		System.out.println("initialize treap repositry "
				+ repository.getAbsolutePath());
	}

	@Override
	public CommentResults getComments(String uri, int page, int count) {
		byte[] bytesOfMessage;
		try {
			bytesOfMessage = uri.getBytes("UTF-8");
			byte[] hash = md.digest(bytesOfMessage);

			int start = page * count;
			int end = start + count;

			List<Comment> list = new ArrayList<Comment>(count);
			Map<FastString, byte[]> map = treap.range(
					new FastString(concat(hash, String.format("%05d", start).getBytes())),
					new FastString(concat(hash, String.format("%05d", end).getBytes())), count);
			for (byte[] b : map.values()) {
				Comment c = (Comment) bytes2Object(b);
				list.add(c);
			}
			CommentResults comments = new CommentResults(page, count,
					getLastCommentNumber(uri), list);

			return comments;

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// TODO: needs to account for possible hash clashes
	@Override
	public int addComment(String uri, Comment c) {
		byte[] bytesOfMessage;
		int n = -1;

		try {
			n = getLastCommentNumber(uri);
			n++;
			c.setId(n);
			bytesOfMessage = uri.getBytes("UTF-8");
			byte[] hash = md.digest(bytesOfMessage);

			byte[] commentNo = String.format("%05d", n).getBytes();
			byte[] key = concat(hash, commentNo);

			treap.put(new FastString(key), object2Bytes(c));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return n;
	}

	private int getLastCommentNumber(String uri)
			throws UnsupportedEncodingException {
		byte[] bytesOfMessage = uri.getBytes("UTF-8");
		byte[] hash = md.digest(bytesOfMessage);

		Map<FastString, byte[]> map = treap.prefix(new FastString(hash), 1,
				null, false);
		if (map.isEmpty()) {
			return 0;
		}

		FastString fs = map.keySet().iterator().next();

		StringBuilder sb = new StringBuilder();
		for (int c = 20; c < 25; c++) {
			sb.append(String.format("%c", fs.bytes[c]));
		}
		return Integer.parseInt(sb.toString());
	}

	private byte[] concat(byte[]... arrays) {
		// Determine the length of the result array
		int totalLength = 0;
		for (int i = 0; i < arrays.length; i++) {
			totalLength += arrays[i].length;
		}

		// create the result array
		byte[] result = new byte[totalLength];

		// copy the source arrays into the result array
		int currentIndex = 0;
		for (int i = 0; i < arrays.length; i++) {
			System.arraycopy(arrays[i], 0, result, currentIndex,
					arrays[i].length);
			currentIndex += arrays[i].length;
		}

		return result;
	}

	private static byte[] object2Bytes(Object o) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(o);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	private static Object bytes2Object(byte[] bytes) {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		Object o = null;
		try {
			ObjectInput in = new ObjectInputStream(bis);
			o = in.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return o;
	}

	private static String bytes2Hex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02X ", b));
		}

		return sb.toString();
	}
}
