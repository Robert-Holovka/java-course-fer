<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Background color chooser</title>
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
	<br><br>
	<h1><a href="setcolor?pickedBgCol=white">WHITE</a></h1> <br>
	<h1><a href="setcolor?pickedBgCol=red">RED</a></h1> <br>
	<h1><a href="setcolor?pickedBgCol=green">GREEN</a></h1> <br>
	<h1><a href="setcolor?pickedBgCol=cyan">CYAN</a></h1> <br>
</body>

</html>