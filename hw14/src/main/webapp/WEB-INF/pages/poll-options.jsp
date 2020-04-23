<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Select poll option</title>
</head>

<body>
	<h1>${poll.title}</h1>
	<h3>${poll.message}</h3>
	<ol>
		<c:forEach var="po" items="${pollOptions}">
			<li><a href="./${request.getContextPath()}glasanje-glasaj?id=${po.id}">${po.optionTitle}</a></li>
		</c:forEach>
	</ol>
</body>

</html>