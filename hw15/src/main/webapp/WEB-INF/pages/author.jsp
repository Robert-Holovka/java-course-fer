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
					<a href="../logout">Log out</a>
				</h2>

			</c:otherwise>
		</c:choose>
	</div>
	<hr>

	<c:choose>
		<c:when test="${entries.isEmpty()}">
			<h1>User has no entries!</h1>
		</c:when>
		<c:otherwise>
			<h1>User blog entries:</h1>
			<c:forEach var="entry" items="${entries}">
				<h4>
					<a href="./${nick}/${entry.id}">${entry.title }</a>
				</h4>
			</c:forEach>
		</c:otherwise>
	</c:choose>

	<c:if test="${sessionScope['current.user.nick'].equals(nick)}">
		<br>
		<br>
		<br>
		<h2>
			<a href="./${request.getContextPath()}${nick}/new">New Blog Entry</a>
		</h2>
	</c:if>

</body>
</html>
