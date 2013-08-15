<%@include file="includes.jsp"%>

<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Register User</h1>


	<form:form action="registerUserProcess" method="POST"
		commandName="user">
		<form:label id="userName" path="userName">User Name: </form:label>
		<form:input type="text" id="userName" path="userName" />
		<br />
		<form:label id="pass" path="password">Password: </form:label>
		<form:input type="text" id="password" path="password" />
		<br />
		<label id="role">Role: </label>
		<form:select id="roles" path="roleId">
			<c:forEach var="role" items="${roles}">
				<option value="${role.key}"
					<c:if test="${selected == role.key}"> selected </c:if>>${role.value}</option>
			</c:forEach>
		</form:select>
		<br />
		<form:label id="mngr" path="managerId">Manager: </form:label>
		<form:input type="text" id="manager" path="managerId" />
		<br />

		<input type="submit" value="Submit" />

	</form:form>



</body>
</html>
