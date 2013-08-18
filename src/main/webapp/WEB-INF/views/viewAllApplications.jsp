<%@include file="includes.jsp"%>

<html>
<head>
<title>Applications</title>
</head>
<body>
	<h1>Applications For User</h1>
	Total applications for user = ${applicationList.size()}
	<br />
	<c:if test="${applicationList.size() > 0}">
		<table border="1">
			<tr>
				<td>Application Number</td>
				<td>From</td>
				<td>Submission Date</td>
				<td>Status</td>
				<td>Content</td>
				<td>Approve</td>
				<td>Reject</td>
			</tr>
			<c:forEach items="${applicationList}" var="application">
				<form:form method="POST" action="updateApplication"
					commandName="updateInput">

					<tr id="${application.id}">

						<td>${application.id}</td>
						<td>${application.from.userName }</td>
						<td>${application.submissionDate}</td>
						<td>${application.status}</td>
						<td>${application.content}</td>
						<td><input type="submit" name="action" value="Approve" /></td>
						<td><input type="submit" name="action" value="Reject" /></td>
					</tr>
					<form:input type="hidden" path="id" value="${application.id}" />
					<form:input type="hidden" path="to" value="${application.to.id}" />
					<form:input type="hidden" path="from" value="${application.from.id}" />
					<form:input type="hidden" path="status"
						value="${application.status}" />
					<form:input type="hidden" path="submissionDate"
						value="${application.submissionDate}" />
					<form:input type="hidden" path="content"
						value="${application.content}" />
				</form:form>
			</c:forEach>
		</table>

	</c:if>
</body>
</html>