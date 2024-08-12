<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <h2>Register</h2>
    <form action="RegistrationServlet" method="post">
        <div>
            <label>First Name:</label>
            <input type="text" name="firstName" required>
        </div>
        <div>
            <label>Last Name:</label>
            <input type="text" name="lastName" required>
        </div>
        <div>
            <label>Address:</label>
            <input type="text" name="address" required>
        </div>
        <div>
            <label>Email:</label>
            <input type="text" name="email" required>
        </div>
        <div>
            <label>Password:</label>
            <input type="password" name="password" required>
        </div>
        <label for="categories">Favorite Categories:</label><br>
        <input type="checkbox" name="categories" value="1"> Indian<br>
        <input type="checkbox" name="categories" value="2"> Mexican<br>
        <input type="checkbox" name="categories" value="3"> Chinese<br>
        <input type="checkbox" name="categories" value="4"> Italian<br>
        <input type="checkbox" name="categories" value="5"> American<br>
        <button type="submit">Register</button>
    </form>
</body>
</html>
