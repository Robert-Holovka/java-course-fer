<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% 
	String pickedColor = session.getAttribute("pickedBgColor") == null ? 
			"" : session.getAttribute("pickedBgColor").toString();
	pageContext.setAttribute("pickedColor", pickedColor);
%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Home page</title>
	<style>
	body {
        background-color: ${empty sessionScope["pickedBgColor"] ? "white" : sessionScope["pickedBgColor"].toString()};
    }
    div {
    	border: 1px solid black;
    }
    </style>
</head>

<body>
	<h1><a href="colors.jsp">Background color chooser</a></h1>
	<h1><a href="stories/funny.jsp">Funny story</a></h1>
	<h1><a href="appinfo.jsp">Server running time</a></h1>
	<h1><a href="report.jsp">Show OS usage</a></h1>
	<h1><a href="./${request.getContextPath()}glasanje">Glasanje za najbolji bend</a></h1>
	<br>
	<div>
		<form action="powers" method="GET">
			Od:<br><input type="number" name="a" min="-100" max="100" step="1" value="1"><br>
			Do:<br><input type="number" name="b" min="-100" max="100" step="1" value="100"><br>
			Do potencije:<br><input type="number" name="n" min="1" max="5" step="1" value="3"><br>
			<input type="submit" value="Potenciraj"><input type="reset" value="Reset">
		</form>
		<h2><a href="./${request.getContextPath()}powers?a=1&b=100&n=3">Potenciraj (default vrijednosti)</a></h2>
	</div>
	<br>
	<div>
		<form action="trigonometric" method="GET">
			Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
			Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
			<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
		</form>
		<h2><a href="./${request.getContextPath()}trigonometric?a=0&b=90">Tabeliraj (default vrijednosti)</a></h2>
	</div>
	
</body>

</html>