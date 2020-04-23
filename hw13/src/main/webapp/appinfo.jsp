<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!
private String getUptime(HttpServletRequest req) {

		ServletContext sc = req.getServletContext();
		long startTime = Long.parseLong(sc.getAttribute("startTime").toString());
		long now = System.currentTimeMillis();

		long uptime = now - startTime;
		long divider = 1000 * 60 * 60 * 24;

		long days = uptime / divider;
		uptime -= days * divider;

		divider = divider / 24;
		long hours = uptime / divider;
		uptime -= hours * divider;
		
		divider = divider / 60;
		long minutes = uptime / divider;
		uptime -= minutes * divider;
		
		divider = divider / 60;
		long seconds = uptime / divider;
		uptime -= seconds * divider;
		
		return String.format("\t%d days\t %d hours\t %d minutes\t %d seconds\t %d miliseconds\t",
				days, hours, minutes, seconds, uptime);
}
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Server uptime</title>
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
	<p>Uptime:<%=getUptime(request)%></p>
</body>

</html>