<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Vote results</title>
<style type="text/css">
	body {
		background-color: ${empty sessionScope["pickedBgColor"] ? "white": sessionScope["pickedBgColor"].toString()};
	}
	table.rez td {
		text-align: center;
	}
</style>
</head>

<body>
	<h1>
		<a href="./index.jsp">Home</a>
	</h1>
	
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="r" items="${results}">
				<tr> 
					<td>${r.name}</td>
					<td>${r.votes}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<br>
	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="${request.getContextPath()}glasanje-grafika" width="400" height="400" />
	
	<br>
	<h2>Rezultati u XLS formatu</h2>
	<p>Rezultati u XLS formatu dostupni su <a href="${request.getContextPath()}glasanje-xls">ovdje</a></p>
	
	<br>
	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach var="w" items="${winners}">
			<li><a href="${w.song}" target="_blank">${w.name}</a>
		</c:forEach>
	</ul>
</body>

</html>