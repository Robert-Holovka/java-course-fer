<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Vote for a favorite band</title>
<style>
	body {
		background-color: ${empty sessionScope["pickedBgColor"] ? "white": sessionScope["pickedBgColor"].toString()};
	}
</style>
</head>

<body>
	<h1>
		<a href="./index.jsp">Home</a>
	</h1>
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
	<ol>
		<c:forEach var="band" items="${bandList}">
			<li><a href="./${request.getContextPath()}glasanje-glasaj?id=${band.ID}">${band.name}</a></li>
		</c:forEach>
	</ol>
</body>

</html>