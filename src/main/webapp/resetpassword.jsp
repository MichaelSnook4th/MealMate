<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reset Password</title>
</head>
<body>
    <h2>Reset Your Password</h2>
    
    <form action="ResetPasswordServlet" method="post">
        <input type="hidden" name="token" value="${token}">
        
        <label for="password">New Password:</label>
        <input type="password" id="password" name="password" required>
        
        <label for="confirmPassword">Confirm New Password:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required>
        
        <button type="submit">Reset Password</button>
    </form>
</body>
</html>