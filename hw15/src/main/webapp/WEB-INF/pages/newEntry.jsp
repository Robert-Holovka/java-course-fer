<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>

	<div>
		<c:choose>
			<c:when test="${empty sessionScope['current.user.id']}">
				<h2>Not logged in!</h2>
			</c:when>
			<c:otherwise>
				<h2>${sessionScope['current.user.ln']}
					${sessionScope['current.user.fn']}</h2>
				<h2>
					<a href="../../logout">Log out</a>
				</h2>

			</c:otherwise>
		</c:choose>
	</div>
	<hr>

	<h1>Create new blog entry:</h1>
	<form method="post" id="entryForm">
  		<h4>Title:</h4>
  		<input type="text" name="title" value="${entry.title}" size="50">
	</form>
	<h4>Content:</h4>
	<textarea rows="4" cols="50" name="text" form="entryForm">${entry.text}</textarea>
	<br>
	<br>
	<input type="submit" form="entryForm" value="Submit">
</body>
</html>
