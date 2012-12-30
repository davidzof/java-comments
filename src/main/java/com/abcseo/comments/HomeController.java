package com.abcseo.comments;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	private Repository repository;

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	private static final int count = 5;

	@RequestMapping(value = { "/*.htm", "/*/*.htm" }, produces = "text/html; charset=utf-8", method = RequestMethod.GET)
	public String main(HttpServletRequest request, Model model, int start) {
		String uri = request.getServletPath();

		Comments comments = repository.getComments(uri, start, count);
		if (comments != null) {
			model.addAttribute("comments", comments);
		}
		model.addAttribute("id", uri);

		return "index";
	}

	@RequestMapping(value = "postComment.do", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String comment(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "comment", required = true) String text,
			@RequestParam(value = "author", required = true) String author,
			Model model) {

		Comment comment = new Comment();
		comment.setComment(text.replaceAll("(\r\n|\n)", "<br />"));
		comment.setAuthor(author);
		repository.addComment(id, comment);
		model.addAttribute("comment", comment);

		return "comment";
	}

}
