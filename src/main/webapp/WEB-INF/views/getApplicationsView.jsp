<%@include file="includes.jsp"%>

<html>
<head>
<title>Applications</title>
</head>
<body>
	<h1>Get Applications For User</h1>

	<br />
	<form:form action="getAllApplicationsForUser" method="POST"
		commandName="getInput">
		<input type="radio" name="type" value="all" />All Applications<br />
		<input type="radio" name="type" value="pending" />Pending Applications<br />
		<form:label id="toUser" path="userId">User ID:  </form:label>
		<form:input type="text" id="to" path="userId" />
		<br />
		<input type="submit" value="Submit" />
	</form:form>



</body>
</html>
