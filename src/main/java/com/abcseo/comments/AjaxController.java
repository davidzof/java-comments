package com.abcseo.comments;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AjaxController implements ServletContextAware {

	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@RequestMapping(value = "postComment.ax", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public String comment(
			@RequestParam(value = "comment", required = true) String comment,
			Model model) {
		ArrayList comments = (ArrayList) servletContext
				.getAttribute("comments");
		if (comments == null) {
			comments = new ArrayList();
			servletContext.setAttribute("comments", comments);
		}
		System.out.println(comment);

		comments.add(comment);
		model.addAttribute("comment", comment);

		return "comment";
	}

}