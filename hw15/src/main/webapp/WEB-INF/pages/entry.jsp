<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>

	<c:if test="${sessionScope['current.user.nick'].equals(nick)}">
		<a href="./${request.getContextPath()}edit/${entry.id}">Edit blog entry</a>
	</c:if>

	<div>
		<h1>${entry.title}</h1>
		<textarea readonly rows="10" cols="50">${entry.text}</textarea>
	</div>
	
	<h1>Comments</h1>
	<c:forEach var="comment" items="${entry.comments}">
		<p>${comment.usersEMail}</p>
		<p>${comment.postedOn}</p>
		<p>${comment.message}</p>
		<hr>
	</c:forEach>


	<h1>Leave a comment:</h1>
	<form action="./${entry.id}/comment" method="post">
		<c:choose>
			<c:when test="${empty sessionScope['current.user.id']}">
				Email: <input type="email" name="email" size="30"> <br>
			</c:when>
			<c:otherwise>
				Email: <input type="email" name="email" size="30" value="${sessionScope['current.user.email']}"> <br>
			</c:otherwise>
		</c:choose>
		Comment: <input type="text" name="message" size="100">
		<input type="submit">
	</form>
</body>
</html>
