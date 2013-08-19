<%@include file="includes.jsp"%>

<html>
<head>
<title>Applications</title>
</head>
<body>
	<h1>Applications For User</h1>
	Total applications for user = ${messages.size()}
	<br />
	<c:if test="${messages.size() > 0}">
		<table border="1">
			<tr>
				<td>Message ID</td>
				<td>From</td>
				<td>Date</td>
				<td>Content</td>
			</tr>
			<c:forEach items="${messages}" var="message">
				<tr id="${message.id}">
					<td>${message.id}<c:if test="${!message.read}">***</c:if></td>
					<td>${message.from.userName}</td>
					<td>${message.creationDate}</td>
					<td>${message.content}</td>
				</tr>
			</c:forEach>
		</table>
		<br />
*** = UNREAD MESSAGE
	</c:if>
</body>
</html>