<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up</title>
</head>
<body>
	<form action="../../AuthController?action=signUp" method="post">
		<div>
			<div style="display: inline-block;">
				<div>
					<label>Login</label>
				</div>
				<div>
					<label>Password</label>
				</div>
				<div>
					<label>First Name</label>
				</div>
				<div>
					<label>Last Name</label>
				</div>
			</div>
			<div style="display: inline-block;">
				<div>
					<input name="login" type="text" />
				</div> 
				<div>
					<input name="pass" type="password" />
				</div>
				<div>
					<input name="fName" type="text" />
				</div> 
				<div>
					<input name="lName" type="text" />
				</div>
			</div>
			<div>
				<a href="/Task3/index.jsp">Back</a>
				<button>Sign Up</button>
			</div>
		</div>
	</form>
</body>
</html>