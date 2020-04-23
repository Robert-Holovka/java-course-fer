<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Select poll</title>
</head>

<body>
	<h1>Select poll:</h1>
	<ul>
		<c:forEach var="p" items="${polls}">
			<li><a href="./${request.getContextPath()}glasanje?pollID=${p.id}">${p.title}</a></li>
		</c:forEach>
	</ul>
</body>

</html>