<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<section id="content" class="body">
	<div class="row">
		<h1>
			<small>Comments</small>
		</h1>
		<ul id="posts-list">
			<c:forEach items="${comments.results}" var="comment">
				<li><a href="#${comment.id}">#</a>
					<p>Posted on ${comment.date}</p>
					<p> by ${comment.author}</p>
					<div class="content"><p>${comment.comment}
					</p></div>
				</li>	
			</c:forEach>
		</ul>
	</div>

	<div class="row">
		<!--  pager -->
		<c:if test="${comments.pager()}">
			<ul class="pagination">
				<c:choose>
					<c:when test="${ ! comments.first() }">
						<li class="arrow"><a class="nav"
							href="?start=${comments.start - 1}">&laquo;</a></li>
					</c:when>
					<c:otherwise>
						<li class="arrow unavailable">&laquo;</li>
					</c:otherwise>
				</c:choose>

				<c:if test="${comments.start >= 4}">
					<li><a class="nav" href="?start=0">1</a></li>
					<li class="unavailable"><a href="">&hellip;</a></li>
				</c:if>

				<c:forEach items="${comments.slip}" var="slip">
					<c:set value="${slip + 1}" var="page" />

					<c:choose>
						<c:when test="${comments.start == slip}">
							<li class="current"><a class="nav" href="?start=${slip}">${page}</a></li>
						</c:when>
						<c:otherwise>
							<li><a class="nav" href="?start=${slip}">${page}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<c:if test="${page < comments.pages - 1}">
					<li class="unavailable"><a href="">&hellip;</a></li>
				</c:if>

				<c:if test="${page < comments.pages}">
					<li><a class="nav" href="?start=${comments.pages - 1}">${comments.pages}</a></li>
				</c:if>

				<c:choose>
					<c:when test="${comments.start == comments.pages - 1}">
						<li class="arrow unavailable"><a href="">&raquo;</a></li>
					</c:when>
					<c:otherwise>
						<li class="arrow"><a class="nav"
							href="?start=${comments.start + 1}">&raquo;</a></li>
					</c:otherwise>
				</c:choose>

			</ul>
		</c:if>
	</div>
</section>