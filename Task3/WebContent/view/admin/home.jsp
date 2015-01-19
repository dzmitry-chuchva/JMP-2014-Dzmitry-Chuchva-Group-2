<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body>
	You are logged in as ${user.firstName} ${user.lastName}.
	<c:forEach items="${accounts}" var="account">
		<div style="display: block;">
			<div style="display: inline;">${account.value}</div>
			<div style="display: inline;">${account.currency.type}</div>
			<div style="display: inline;">${account.user.firstName}</div>
			<div style="display: inline;">${account.user.lastName}</div>
		</div>
	</c:forEach>
	<a href="/Task3/index.jsp">Back</a>
	<div style="display: inline;"><a href="/Task3/AdminController/newAccount">Add Account</a></div>
</body>
</html>