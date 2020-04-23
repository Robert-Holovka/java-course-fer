<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Error</title>
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
	
	<p>${error}</p>
</body>

</html>