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
					<td><c:if test="${!message.read}">
							<b>
						</c:if>${message.id} <c:if test="${!message.read}">
							</b>
						</c:if></td>
					<td><c:if test="${!message.read}">
							<b>
						</c:if>${message.from.userName} <c:if test="${!message.read}">
							</b>
						</c:if></td>
					<td><c:if test="${!message.read}">
							<b>
						</c:if>${message.creationDate} <c:if test="${!message.read}">
							</b>
						</c:if></td>
					<td><c:if test="${!message.read}">
							<b>
						</c:if><a href="/leaveapplicationUI/messageDetails?id=${message.id}">${message.content}</a>
						<c:if test="${!message.read}">
							</b>
						</c:if></td>
				</tr>
				<c:if test="${!message.read}">
					</b>
				</c:if>
			</c:forEach>
		</table>
		<br />
*** = UNREAD MESSAGE
	</c:if>
</body>
</html>