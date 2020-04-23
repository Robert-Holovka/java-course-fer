<%@ page import="java.awt.Color" import="java.util.Random" 
contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!
private String randomColor() {
	Random rand = new Random();
	int r = rand.nextInt(255);
	int g = rand.nextInt(255);
	int b = rand.nextInt(255);
	
	return String.format("#%02x%02x%02x", r, g, b);  
}
%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Funny story</title>
	<style>
	body {
        background-color: ${empty sessionScope["pickedBgColor"] ? "white" : sessionScope["pickedBgColor"].toString()};
    }
    p, h1 {
    	color: <%= randomColor() %>;
    }
    </style>
</head>

<body>	
	<h1><a href="../index.jsp">Home</a></h1>
	<br>
	<h1>Hunting gone wrong</h1>
	<p>Two hunters are out in the woods when one of them collapses. 
	He’s not breathing and his eyes are glazed. The other guy whips out his cell phone and calls 911.</p>
	<p>“I think my friend is dead!” he yells. “What can I do?”</p>
	<p>The operator says, “Calm down. First, let’s make sure he’s dead.”</p>
	<p>There’s a silence, then a shot. Back on the phone, the guy says, “OK, now what?”</p>
</body>

</html>