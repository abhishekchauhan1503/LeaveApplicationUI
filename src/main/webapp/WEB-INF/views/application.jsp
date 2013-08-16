<%@include file="includes.jsp"%>

<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Register User</h1>


	<form:form action="saveapplication" method="POST"
		commandName="application">
		<form:label id="toUser" path="to">To:  </form:label>
		<form:input type="text" id="to" path="to" />
		<br />
		<form:label id="fromUser" path="from">From: </form:label>
		<form:input type="text" id="from" path="from" />
		<br />
		<label id="Content">Content: </label>
		<form:input type="text" id="content" path="content" />
		<br />
		<form:label id="subDate" path="submissionDate">Date: </form:label>
		<form:input type="text" id="date" path="submissionDate" />
		<br />

		<input type="submit" value="Submit" />

	</form:form>



</body>
</html>
