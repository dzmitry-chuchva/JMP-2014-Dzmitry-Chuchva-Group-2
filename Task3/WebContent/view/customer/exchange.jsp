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
	<form action="/Task3/CustomerController/completeExchange/${user.id}" method="post">
		<div style="display: block;">
			<div style="display: inline;">
				<select name="accountFromId">
					<c:forEach items="${accounts}" var="account">
						<option value="${account.id}">${account.currency.type} -
							${account.value}</option>
					</c:forEach>
				</select>
			</div>
			<div style="display: inline;">
				<select name="accountToId">
					<c:forEach items="${accounts}" var="account">
						<option value="${account.id}">${account.currency.type} -
							${account.value}</option>
					</c:forEach>
				</select>
			</div>
			<div style="display: inline;">
				<input name="exchangeValue" type="text">
			</div>
		</div>
		<div style="display: block;">
			<div style="display: inline;">
				<a href="/Task3/index.jsp">Back</a>
			</div>
			<div style="display: inline;">
				<button>Complete</button>
			</div>
		</div>
	</form>
</body>
</html>