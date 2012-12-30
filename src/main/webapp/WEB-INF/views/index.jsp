<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<!-- paulirish.com/2008/conditional-stylesheets-vs-css-hacks-answer-neither/ -->
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="en">
	<!--<![endif]-->
<head>
		<meta charset="utf-8" />

		<!-- Set the viewport width to device width for mobile -->
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>Comment System</title>
		<!-- Included CSS Files (Compressed) -->
		<link rel="stylesheet" href="foundation/stylesheets/foundation.min.css">
		<link rel="stylesheet" href="foundation/stylesheets/app.css">
		
		<script src="foundation/javascripts/modernizr.foundation.js"></script>

		<!-- IE Fix for HTML5 Tags -->
		<!--[if lt IE 9]>
		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
</head>
<body>
	<nav class="top-bar">
		<ul>
			<li class="name">
				<h1><a href="http://www.abcseo.com/">ABCSEO</a></h1>
			</li>
		</ul>
	</nav>
		
	<div class="twelve columns">
		<section id="content" class="body">
			<div class="row">
				<h1><small>Comments</small></h1>		
				<ol id="posts-list">
				<c:forEach items="${comments.results}" var="comment">
					<li>
						<article id="1" class="hentry">
							<footer class="post-info">
								<abbr class="published" title="${comment.date}"> ${comment.date}</abbr>
								<address class="vcard author">
									By <a class="url fn" href="#">${comment.author}</a>
								</address>
							</footer>
							<div class="entry-content">
								<p>
									${comment.comment}
								</p>
							</div>
						</article>
					</li>
				</c:forEach>
			</ol>
			</div>
			
			<div class="row">
				<!--  pager -->
				<c:if test="${comments.pager()}">
				<ul class="pagination">
			
				<c:choose>
				<c:when test="${ ! comments.first() }">
					<li class="arrow"><a href="?start=${comments.start - 1}&end=${comments.count}">&laquo;</a></li>
				</c:when>
				<c:otherwise>
					<li class="arrow unavailable">&laquo;</li>
				</c:otherwise>
				</c:choose>
				
				<c:if test="${comments.start >= 4}" >
					<li><a href="?start=0&end=${comments.count}">1</a></li>
					<li class="unavailable"><a href="">&hellip;</a></li>
				</c:if>
								
				<c:forEach items="${comments.slip}" var="slip">
					<c:set value="${slip + 1}" var="page" />

					<c:choose>
					<c:when test="${comments.start == slip}">
						<li class="current"><a href="?start=${slip}&end=${comments.count}">${page}</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="?start=${slip}&end=${comments.count}">${page}</a></li>
					</c:otherwise>
					</c:choose>
				</c:forEach>
				
				<c:if test="${page < comments.pages - 1}" >
					<li class="unavailable"><a href="">&hellip;</a></li>
				</c:if>
				
				<c:if test="${page < comments.pages}">
					<li><a href="?start=${comments.pages - 1}&end=${comments.count}" >${comments.pages}</a></li>
				</c:if>

				<c:choose>
				<c:when test="${comments.start == comments.pages - 1}">
					<li class="arrow unavailable"><a href="">&raquo;</a></li>
				</c:when>
				<c:otherwise>
					<li class="arrow"><a href="?start=${comments.start + 1}&end=${comments.count}">&raquo;</a></li>
				</c:otherwise>
				</c:choose>
					
				</ul>
			</c:if>
			</div>
		</section>
	
		<section id="comments" class="body">
			<div class="row">
			<h3>Leave a Comment</h3>
				<form action="postComment.do" method="post" id="respond">
		
					<label for="author" class="required">Your name</label>
					<input type="text" name="author" id="author" value="" tabindex="1"
						required="required"> <label for="email" class="required">Your
						email;</label> <input type="email" name="email" id="email" value=""
						tabindex="2" required="required"> <label for="comment"
						class="required">Your message</label>
					<textarea name="comment" id="comment" rows="5" tabindex="4" required="required"></textarea>
					<input type="hidden" name="id" value="${id}" id="comment_post_ID" />
						
						<input class="small button" name="submit" type="submit" value="Submit comment" />
		
				</form>
			</div>
		</section>
	</div>

	<!-- Included JS Files (Compressed) -->
	<script src="foundation/javascripts/jquery.js"></script>
	<script src="foundation/javascripts/foundation.min.js"></script>

	<!-- Initialize JS Plugins -->
	<script src="foundation/javascripts/app.js"></script>
	
	<script type="text/javascript">
	$(function() {
		$('#respond').submit(handleSubmit);
	});

	function handleSubmit() {
		var form = $(this);
		var data = {
			"author" : form.find('#author').val(),
			"email" : form.find('#email').val(),
			"comment" : form.find('#comment').val(),
			"id" : form.find('#comment_post_ID').val()
		};

		postComment(data);

		return false;
	}

	function postComment(data) {
		$.ajax({
			type : 'POST',
			url : 'postComment.do',
			data : data,
			headers : {
				'X-Requested-With' : 'XMLHttpRequest'
			},
			success : postSuccess,
			error : postError
		});
	}

	function postSuccess(data, textStatus, jqXHR) {
		$('#respond').get(0).reset();
		displayComment(data);
	}

	function displayComment(data) {
		var commentHtml = createComment(data);
		var commentEl = $(commentHtml);
		commentEl.hide();
		var postsList = $('#posts-list');
		postsList.addClass('has-comments');
		postsList.append(commentEl);
		commentEl.slideDown();
	}

	function createComment(data) {
		var html = '' + '<li><article id="' + data.id + '" class="hentry">'
				+ '<footer class="post-info">'
				+ '<abbr class="published" title="' + data.date + '">'
				+ parseDisplayDate(data.date) + '</abbr>'
				+ '<address class="vcard author">'
				+ 'By <a class="url fn" href="#">' + data.author
				+ '</a>' + '</address>' + '</footer>'
				+ '<div class="entry-content">' + '<p>' + data.comment + '</p>'
				+ '</div>' + '</article></li>';

		return html;
	}

	function parseDisplayDate(date) {
		date = (date instanceof Date ? date : new Date(Date.parse(date)));
		var display = date.getDate()
				+ ' '
				+ [ 'January', 'February', 'March', 'April', 'May', 'June',
						'July', 'August', 'September', 'October', 'November',
						'December' ][date.getMonth()] + ' '
				+ date.getFullYear();
		return display;
	}

/*	$(function() {

		$(document).keyup(function(e) {
			e = e || window.event;
			if (e.keyCode === 85) {
				displayComment({
					"id" : "comment_1",
					"comment_post_ID" : 1,
					"date" : "Tue, 21 Feb 2012 18:33:03 +0000",
					"comment" : "The realtime Web rocks!",
					"author" : "Phil Leggetter"
				});
			}
		});

	});
*/
	function postError(jqXHR, textStatus, errorThrown) {
		alert('posting error ' + textStatus);
	}
	</script>
</body>
</html>
