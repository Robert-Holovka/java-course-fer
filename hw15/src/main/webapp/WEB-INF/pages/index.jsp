<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Home</title>
<style type="text/css">
h2 {
	display: inline-block;
	margin-right: 50px;
	margin-left: 50px;
}

.element {
	margin-top: 10px;
	padding-left: 155px;
}

.greska {
	margin-top: 10px;
	font-family: fantasy;
	font-weight: bold;
	font-size: 0.9em;
	color: #FF0000;
	padding-left: 155px;
}

.formLabel {
	display: inline-block;
	width: 140px;
	font-weight: bold;
	text-align: right;
	padding-right: 10px;
}

.formControls {
	margin-top: 10px;
}

.box {
	padding-top: 5px;
}
</style>
</head>

<body>
	<div>
		<c:choose>
			<c:when test="${empty sessionScope['current.user.id']}">
				<h2>Not logged in!</h2>
			</c:when>
			<c:otherwise>
				<h2>${sessionScope['current.user.ln']}
					${sessionScope['current.user.fn']}</h2>
				<h2>
					<a href="./${request.getContextPath()}logout">Log out</a>
				</h2>

			</c:otherwise>
		</c:choose>
		<h2>
			<a class="formLabel" href="./register">Create an account</a>
		</h2>
	</div>
	<hr>

	<c:if test="${empty sessionScope['current.user.id']}">
		<br>
		<br>
		<form action="login" method="post">
			<div>
				<div class="box">
					<span class="formLabel">Nick</span> <input type="text" name="nick"
						value="${form.nick}" required minlength=3 maxlength=20
						value='<c:out value="${user.nick}"/>' size="20">
				</div>
				<c:if test="${form.hasError('nick')}">
					<div class="greska">
						<c:out value="${form.getErrorMessage('nick')}" />
					</div>
				</c:if>
			</div>

			<div>
				<div class="box">
					<span class="formLabel">Password</span> <input type="password"
						name="password" required minlength="8" maxlength="20" size="20">
				</div>
			</div>
			<c:if test="${form.hasError('password')}">
				<div class="greska">
					<c:out value="${form.getErrorMessage('password')}" />
				</div>
			</c:if>


			<div class="greska"><input
					type="submit" name="login" value="Login">
			</div>
		</form>
		<br>
		<br>
		<br>
	</c:if>


	<h1 class="element">Authors:</h1>
	<c:forEach var="user" items="${users}">
		<h3><a class="element" href="./${request.getContextPath()}author/${user.nick}">${user.lastName}
			${user.firstName}</a></h3>
	</c:forEach>
</body>
</html>

