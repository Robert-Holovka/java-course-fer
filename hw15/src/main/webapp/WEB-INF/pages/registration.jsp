<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Registration</title>
<style type="text/css">

h1 {
	color: green;
	margin-left: 150px;
	margin-top: 10px;
}

.greska {
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
	<h1>Registration</h1>

	<form action="register" method="post">
		
		<div>
			<div class="box">
				<span class="formLabel">First Name</span>
				<input type="text" name="firstName" value="${form.firstName}"
				required  minlength="2" maxlength="30"
				value='<c:out value="${user.firstName}"/>' size="30">
			</div>
			<c:if test="${form.hasError('firstName')}">
				<div class="greska">
					<c:out value="${form.getErrorMessage('firstName')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div class="box">
				<span class="formLabel">Last Name</span>
				<input type="text" name="lastName" value="${form.lastName}"
				required minlength="2" maxlength="30"
				value='<c:out value="${user.lastName}"/>' size="30">
			</div>
			<c:if test="${form.hasError('lastName')}">
				<div class="greska">
					<c:out value="${form.getErrorMessage('lastName')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div class="box">
				<span class="formLabel">Email</span>
				<input type="text" name="email" value="${form.email}"
				required pattern="(.+)@(.+)(\.)(.+)"
				value='<c:out value="${user.email}"/>' size="30">
			</div>
			<c:if test="${form.hasError('email')}">
				<div class="greska">
					<c:out value="${form.getErrorMessage('email')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div class="box">
				<span class="formLabel">Nick</span>
				<input type="text" name="nick" value="${form.nick}"
				required minlength=3 maxlength=20
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
				<span class="formLabel">Password</span>
				<input type="password" name="password" required minlength="8" maxlength="20" size="20">
			</div>
			<c:if test="${form.hasError('password')}">
				<div class="greska">
					<c:out value="${form.getErrorMessage('password')}" />
				</div>
			</c:if>
		</div>
		
		<div>
			<div class="box">
				<span class="formLabel">Confirm Password</span>
				<input type="password" name="confirmPassword" required minlength="8" maxlength="20" size="20">
			</div>
			<c:if test="${form.hasError('confirmPassword')}">
				<div class="greska">
					<c:out value="${form.getErrorMessage('confirmPassword')}" />
				</div>
			</c:if>
		</div>
		
		<div class="formControls">
			<br>
			<span class="formLabel">&nbsp;</span> 
			<input type="submit" name="method" value="Save">
			<a href="./main"> Cancel </a>
		</div>
	
	</form>

</body>
</html>
