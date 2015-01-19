<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Account</title>
</head>
<body>
	<form:form method="POST" action="/Task3/AdminController/saveAccount" commandName="account">
		<div>
			<form:select path="user.id" items="${users}" />
		</div>
		<div style="display: block;">
			<div style="display: inline;">
				<form:input path="value"></form:input>
			</div>
			<div style="display: inline;">
				<form:select path="currency.id" items="${currencyTypes}" />
			</div>
		</div>
		<div style="display: block;">
			<div style="display: inline;">
				<a href="/Task3/index.jsp">Back</a>
			</div>
			<div style="display: inline;">
				<button>Save</button>
			</div>
		</div>
	</form:form>
</body>
</html>