<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forgot Password</title>
</head>
<body>
    <h2>Forgot Password</h2>
    <form action="PasswordRecoveryServlet" method="post">
        <label for="email">Enter your email address:</label><br>
        <input type="email" id="email" name="email" required><br><br>
        <input type="submit" value="Recover Password">
    </form>
</body>
</html>