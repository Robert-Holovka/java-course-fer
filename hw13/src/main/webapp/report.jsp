<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>OS usage report</title>
	<style>
	body {
        background-color: ${empty sessionScope["pickedBgColor"] ? "white" : sessionScope["pickedBgColor"].toString()};
    }
    </style>
</head>

<body>	
	<h1>
		<a href="./index.jsp">Home</a>
	</h1>
	<br>
	<h1>OS usage</h1>
	<p>Here are the results of OS usage in survey that we completed.</p>
	<img alt="Pie chart" src="reportImage" width="600" height="400">
</body>

</html>