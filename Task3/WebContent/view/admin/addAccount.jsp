<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Account</title>
</head>
<body>
	<form action="AdminController?action=createAccount" method="post">
		<div>
			<select name="userId">
				<c:forEach items="${users}" var="user">
					<option value="${user.id}">${user.login}</option>
				</c:forEach>
			</select>
		</div>
		<div style="display: block;">
			<div style="display: inline;">
				<input type="text" name="value"></input>
			</div>
			<div style="display: inline;">
				<select name="currencyId">
					<c:forEach items="${currencyTypes}" var="currency">
						<option value="${currency.id}">${currency.type}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div style="display: block;">
			<div style="display: inline;">
				<a href="index.jsp">Back</a>
			</div>
			<div style="display: inline;">
				<button>Create</button>
			</div>
		</div>
	</form>
</body>
</html>