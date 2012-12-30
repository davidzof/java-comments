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
				<h1>
					<a href="http://www.abcseo.com/">Java Comment System</a>
				</h1>
			</li>
		</ul>
	</nav>

	<div class="twelve columns">
		<div class="row">
			<h3>${id}</h3>
			<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis
				elementum euismod velit, eget tincidunt nunc egestas et. Morbi
				egestas cursus interdum. Maecenas luctus, urna rhoncus consequat
				ultrices, turpis felis facilisis erat, sed varius lorem neque vitae
				eros. Nulla auctor quam id dui tristique viverra. Vestibulum dapibus
				mauris ac elit dignissim scelerisque. Nulla facilisi. Etiam congue
				sem id orci interdum eget posuere sapien condimentum. Integer et
				suscipit erat. Vestibulum a neque ante. Integer sed diam nunc, sed
				tempor metus. Sed vehicula urna sit amet quam interdum pulvinar.
				Vestibulum non lacinia tortor. Donec sit amet lectus lectus.</p>
		</div>
		<div class="row">
			<div id="loading">
				<img src="images/loading.gif" alt="loading" />
			</div>
		</div>

		<section id="content" class="body"></section>

		<section id="comments" class="body">
			<div class="row">
				<h3>Leave a Comment</h3>
				<form action="postComment.do" method="post" id="respond">

					<label for="author" class="required">Your name</label> <input
						type="text" name="author" id="author" value="" tabindex="1"
						required="required"> <label for="email" class="required">Your
						email</label> <input type="email" name="email" id="email" value=""
						tabindex="2" required="required"> <label for="comment"
						class="required">Your message</label>
					<textarea name="comment" id="comment" rows="5" tabindex="4"
						required="required"></textarea>
					<input type="hidden" name="id" value="${id}" id="comment_post_ID" />

					<input class="small button" name="submit" type="submit"
						value="Submit comment" />

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

			$("#loading").ajaxStart(function() {
				$(this).show();
			}).ajaxStop(function() {
				$(this).hide();
			});

			$.ajax({
				url : "?start=0",
				type : "post",
				success : function(result) {
					var postsList = $('#content');
					postsList.replaceWith(result);
				}
			});

			$('body').on('click', '.nav', function(e) {
				e.preventDefault();
				var target = e.currentTarget;

				$.ajax({
					url : target,
					type : "post",
					success : function(result) {
						var postsList = $('#content');
						postsList.replaceWith(result);
					}
				});
			});
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
					+ data.date + '</abbr>' + '<address class="vcard author">'
					+ 'By <a class="url fn" href="#">' + data.author + '</a>'
					+ '</address>' + '</footer>'
					+ '<div class="entry-content">' + '<p>' + data.comment
					+ '</p>' + '</div>' + '</article></li>';

			return html;
		}

		function postError(jqXHR, textStatus, errorThrown) {
			alert('posting error ' + textStatus);
		}
	</script>
</body>
</html>
