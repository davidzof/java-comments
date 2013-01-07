/*
 * Copyright 2011-2012, David George, Licensed under the Apache License,
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CommentResults {
	private List<Comment> results;
	private int start;
	private int itemsPerPage;
	private int total; // total results

	public CommentResults(int start, int itemsPerPage, int total,
			List<Comment> results) {
		this.start = start;
		this.itemsPerPage = itemsPerPage;
		this.total = total;
		this.results = results;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCount() {
		return itemsPerPage;
	}

	public int getTotal() {
		return total;
	}

	public void setCount(int count) {
		this.itemsPerPage = count;
	}

	public Iterator<Comment> getResults() {
		return results.iterator();
	}

	public boolean pager() {
		return (total > itemsPerPage) ? true : false;
	}

	public boolean first() {
		return (start == 0) ? true : false;
	}

	public int getPages() {
		return 1 + (total / itemsPerPage);
	}

	/**
	 * Returns a <b>S</b>liding <b>L</b>ist of <b>I</b>ndices for <b>P</b>ages
	 * of items.
	 * 
	 * <p>
	 * Essentially, this returns a list of item indices that correspond to
	 * available pages of items (as based on the set items-per-page). This makes
	 * it relativly easy to do a google-ish set of links to available pages.
	 * </p>
	 * 
	 * <p>
	 * Note that this list of Integers is 0-based to correspond with the
	 * underlying result indices and not the displayed page numbers (see
	 * {@link #getPageNumber}).
	 * </p>
	 * 
	 * @return {@link List} of Integers representing the indices of result pages
	 *         or empty list if there's one or less pages available
	 */
	public List<Integer> getSlip() {
		int slipSize = 4;

		/* return an empty list if there's no pages to list */
		int totalPgs = getPages();
		if (totalPgs <= 1) {
			return Collections.EMPTY_LIST;
		}

		/*
		 * start at zero or just under half of max slip size this keeps
		 * "forward" and "back" pages about even but gives preference to
		 * "forward" pages
		 */
		int slipStart = Math.max(0, (start - (slipSize / 2)));
		System.out.println("slip start " + slipStart);

		/* push slip end as far as possible */
		int slipEnd = Math.min(totalPgs, (slipStart + slipSize));

		/*
		 * if we're out of "forward" pages, then push the slip start toward zero
		 * to maintain slip size
		 */
		if (slipEnd - slipStart < slipSize) {
			slipStart = Math.max(0, slipEnd - slipSize);
		}

		/* convert 0-based page numbers to indices and create list */
		List<Integer> slip = new ArrayList<Integer>(slipEnd - slipStart);
		for (int i = slipStart; i < slipEnd; i++) {
			slip.add(Integer.valueOf(i));
		}
		return slip;
	}
}