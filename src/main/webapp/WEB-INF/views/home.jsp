<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hello world!</h1>
	<a href="/leaveapplicationUI/registerUser">Register User</a>
	<a href="/leaveapplicationUI/application">New Application</a>
	<a href="/leaveapplicationUI/getAllApplicationsForUserView">View
		Applications</a>
	<a href="/leaveapplicationUI/getMessagesForUserView">View Messages</a>
	<a href="/leaveapplicationUI/login">Login</a>

	<br />

	<P>The time on the server is ${serverTime}.</P>

	<select id="roles">
		<c:forEach var="role" items="${roles}">
			<option value="${role.key}"
				<c:if test="${selected == role.key}"> selected </c:if>>${role.value}</option>
		</c:forEach>
	</select> Selected = ${selected }

</body>
</html>
