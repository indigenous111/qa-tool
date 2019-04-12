<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<title>QA Tool | Ticket</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div id="content">
		<h2>Ticket List</h2>
		<div id="message" style="color: green;">
			<c:if test="${not empty msg}">
				<small>${msg}</small>
			</c:if>
		</div>
		<div id="list-tickets">
			<table>
				<thead>
					<tr>
						<th>S.No.</th>
						<th>Project</th>
						<th>Summary</th>
						<th>Created On</th>
						<th>Status</th>
					</tr>
					<c:forEach items="${tickets}" var="ticket" varStatus="counter">
						<tr>
							<td>${counter.index + 1}</td>
							<td>${ticket.project.name}</td>
							<td><a href="<c:url value='/ticket/view/${ticket.id}'/>">${ticket.summary}</a></td>
							<td>${ticket.createdOn}</td>
							<td>${ticket.status.status}</td>
						</tr>
					</c:forEach>
			</table>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>