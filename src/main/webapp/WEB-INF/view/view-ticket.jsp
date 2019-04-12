<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<title>QA Tool | Ticket</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div id="handle" style="float: right">
		<a href="<c:url value='/ticket/edit/${ticket.id}'/>">Edit</a>
	</div>
	<div id="content">
		<h2>View Ticket</h2>
		<fieldset>
			<legend>Ticket</legend>
			<table>
				<tr>
					<td><label>Project:</label></td>
					<td>${ticket.project.name}</td>
				</tr>
				<tr>
					<td><label>Type:</label></td>
					<td>${ticket.type.type}</td>
				</tr>
				<tr>
					<td><label>Status:</label></td>
					<td>${ticket.status.status}</td>
				</tr>
				<tr>
					<td><label>Summary:</label></td>
					<td>${ticket.summary}</td>
				</tr>
				<tr>
					<td><label>Description:</label></td>
					<td>${ticket.description}</td>
				</tr>
			</table>
		</fieldset>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>