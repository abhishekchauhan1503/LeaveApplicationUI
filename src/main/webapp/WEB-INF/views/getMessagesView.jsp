<%@include file="includes.jsp"%>

<html>
<head>
<title>Applications</title>
</head>
<body>
	<h1>Get Applications For User</h1>

	<br />
	<form:form action="getMessagesForUser" method="POST"
		commandName="getInput">
		<form:label id="toUser" path="userId">User ID:  </form:label>
		<form:input type="text" id="to" path="userId" />
		<br />
		<input type="submit" value="Submit" />
	</form:form>



</body>
</html>
