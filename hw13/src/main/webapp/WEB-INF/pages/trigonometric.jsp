<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Background color chooser</title>
<style>
	body {
		background-color: ${empty sessionScope["pickedBgColor"] ? "white": sessionScope["pickedBgColor"].toString()};
	}
	table, td, th {
		border: 1px solid black;
	}
</style>
</head>

<body>
	<h1>
		<a href="./index.jsp">Home</a>
	</h1>
	<table>
		<tr>
			<th>x</th>
			<th>sin(x)</th>
			<th>cos(x)</th>
		</tr>
		<c:forEach var="trig" items="${trigonometrics}">
			<tr>
				<td>${trig.x}</td>
				<td>${trig.sin}</td>
				<td>${trig.cos}</td>
			</tr>		
		</c:forEach>
	</table>
</body>

</html>