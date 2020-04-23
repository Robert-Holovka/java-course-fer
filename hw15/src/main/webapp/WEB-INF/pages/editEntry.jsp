<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>

	<form method="post" id="usrform">
  		Title <input type="text" name="title" value="${entry.title}">
  		<input type="submit">
	</form>
	<br>
	<textarea rows="4" cols="50" name="text" form="usrform">${entry.text}</textarea>

</body>
</html>
