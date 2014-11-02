<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign In</title>
</head>
<body>
	<form action="../../AuthController?action=signIn" method="post">
		<div>
			<div style="display: inline-block;">
				<div>
					<label>Login</label>
				</div>
				<div>
					<label>Password</label>
				</div>
			</div>
			<div style="display: inline-block;">
				<div>
					<input name="login" type="text" />
				</div> 
				<div>
					<input name="pass" type="password" />
				</div>
			</div>
			<div>
				<a href="../../index.jsp">Back</a>
				<button>Sign In</button>
			</div>
		</div>
	</form>
</body>
</html>