<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">

</head>
<body>
	<h2>Login</h2>
	<form action="LoginServlet" method="post">
		<div>
			<label>Email:</label>
			<input type="text" name="email" required>
		</div>
		<div>
			<label>Password:</label>
			<input type="password" name="password" required>
		</div>
		<button type="submit">Login</button>
	</form>
	<p>Don't have an account? <a href="registration.jsp">Register here</a></p>
	<p><a href="forgotpassword.jsp">Forgot Password?</a></p>
</body>
</html>