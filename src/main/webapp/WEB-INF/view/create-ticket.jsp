<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>QA Tool | Ticket</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div id="content">
		<h2>Create Ticket</h2>
		<form:form method="post" modelAttribute="ticket">
			<fieldset>
				<legend>Ticket</legend>
				<table>
					<tr>
						<td><label>Select Project:</label></td>
						<td><form:select path="project.id">
								<form:option value="-1"> -- SELECT ONE -- </form:option>
								<form:options items="${projects}" itemLabel="name"
									itemValue="id" />
							</form:select></td>
					</tr>
					<tr>
						<td><label>Select Type:</label></td>
						<td><form:select path="type.id">
								<form:option value="-1"> -- SELECT ONE -- </form:option>
								<form:options items="${ticketTypes}" itemLabel="type"
									itemValue="id" />
							</form:select></td>
					</tr>
					<tr>
						<td><label>Summary:</label></td>
						<td><form:textarea rows="3" cols="150" path="summary"></form:textarea></td>
					</tr>
					<tr>
						<td><label>Description:</label></td>
						<td><form:textarea rows="20" cols="150" path="description"></form:textarea></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center;"><input
							type="submit" value="Create" /></td>
					</tr>
				</table>
			</fieldset>
		</form:form>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>