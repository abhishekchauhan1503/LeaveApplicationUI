<%@include file="includes.jsp"%>

<html>
<head>
<title>Applications</title>
</head>
<body>
	<h1>Applications For User</h1>
	<br />
	<c:if test="${message!=null}">
		<table border="1">
			<tr>
				<td>Message ID</td>
				<td>${message.id}</td>

			</tr>
			<tr>
				<td>From</td>
				<td>${message.from.userName}</td>
			</tr>
			<tr>
				<td>Date</td>
				<td>${message.creationDate}</td>
			</tr>
			<tr>
				<td>Content</td>
				<td>${message.content}</td>
			</tr>

		</table>
		<br />
	</c:if>
</body>
</html>