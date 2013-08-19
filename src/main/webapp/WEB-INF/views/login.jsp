<%@include file="includes.jsp"%>

<html>
<head>
<title>Login</title>
</head>
<body>
	<c:if test="${user!=null}">
		<c:if test="${!authenticated}">
			<h1>INVALID CREDENTIALS.</h1>
			<br />
		</c:if>
	</c:if>
	<form:form action="authenticateUser" method="POST"
		commandName="authenticationInput">
		<form:label id="userNme" path="userName">User Name:  </form:label>
		<form:input type="text" id="userName" path="userName" />
		<br />
		<form:label id="passwrd" path="password">Password:  </form:label>
		<form:input type="text" id="password" path="password" />
		<br />
		<input type="submit" value="Submit" />
	</form:form>
</body>
</html>